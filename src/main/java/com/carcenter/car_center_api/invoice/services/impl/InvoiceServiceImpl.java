package com.carcenter.car_center_api.invoice.services.impl;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse;
import com.carcenter.car_center_api.invoice.entities.Invoice;
import com.carcenter.car_center_api.invoice.mappers.InvoiceMapper;
import com.carcenter.car_center_api.invoice.repositories.InvoiceRepository;
import com.carcenter.car_center_api.invoice.services.InvoiceServiceInterface;
import com.carcenter.car_center_api.invoicedetail.mappers.InvoiceDetailMapper;
import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.entities.MaintenanceStatus;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import com.carcenter.car_center_api.maintenancesparepart.entities.MaintenanceSparePart;
import com.carcenter.car_center_api.maintenancesparepart.repositories.MaintenanceSparePartRepository;
import com.carcenter.car_center_api.invoicedetail.entities.InvoiceDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceServiceInterface {

    private static final BigDecimal SPARE_PARTS_DISCOUNT_THRESHOLD = new BigDecimal("3000000");
    private static final BigDecimal LABOR_DISCOUNT_RATE = new BigDecimal("0.5");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.19");

    private final ClientRepository clientRepo;
    private final MaintenanceRepository maintenanceRepo;
    private final MaintenanceSparePartRepository mspRepo;
    private final MaintenanceServiceItemRepository msiRepo;
    private final InvoiceRepository invoiceRepo;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceDetailMapper invoiceDetailMapper;

    @Override
    public InvoiceCreateResponse generateInvoice(Integer document) {
        Client client = fetchClientByDocument(document);
        List<Maintenance> maintenances = fetchMaintenancesToInvoice(client.getId());

        if (maintenances.isEmpty()) {
            throw new IllegalArgumentException("No completed maintenances to invoice");
        }

        List<InvoiceDetail> invoiceDetails = new ArrayList<>();
        BigDecimal invoiceSubtotal = BigDecimal.ZERO;

        for (Maintenance maintenance : maintenances) {
            BigDecimal maintenanceTotal = calculateMaintenanceCosts(maintenance, invoiceDetails);
            validateBudget(maintenance, maintenanceTotal);
            maintenance.setInvoiced(true);
            invoiceSubtotal = invoiceSubtotal.add(maintenanceTotal);
        }

        BigDecimal tax = invoiceSubtotal.multiply(TAX_RATE);
        BigDecimal invoiceTotal = invoiceSubtotal.add(tax);

        Invoice invoice = saveInvoice(client, invoiceDetails, invoiceSubtotal, tax, invoiceTotal);

        return invoiceMapper.toResponse(client, maintenances, invoice, invoiceDetails);
    }

    // ==================== Métodos Privados ====================

    private Client fetchClientByDocument(Integer document) {
        return clientRepo.findByDocument(document)
                .orElseThrow(() -> new IllegalArgumentException("Client with document " + document + " not found"));
    }

    private List<Maintenance> fetchMaintenancesToInvoice(Long clientId) {
        return maintenanceRepo.findAll().stream()
                .filter(m -> m.getClient().getId().equals(clientId))
                .filter(m -> m.getStatus() == MaintenanceStatus.COMPLETED && !m.isInvoiced())
                .toList();
    }

    private BigDecimal calculateMaintenanceCosts(Maintenance maintenance, List<InvoiceDetail> invoiceDetails) {
        BigDecimal sparePartsTotal = calculateSparePartsCost(maintenance, invoiceDetails);
        BigDecimal servicesTotal = calculateServicesCost(maintenance, sparePartsTotal, invoiceDetails);

        BigDecimal serviceDiscount = sparePartsTotal.compareTo(SPARE_PARTS_DISCOUNT_THRESHOLD) > 0
                ? servicesTotal.multiply(LABOR_DISCOUNT_RATE)
                : BigDecimal.ZERO;

        return sparePartsTotal.add(servicesTotal).subtract(serviceDiscount);
    }

    private BigDecimal calculateSparePartsCost(Maintenance maintenance, List<InvoiceDetail> invoiceDetails) {
        List<MaintenanceSparePart> spareParts = mspRepo.findByMaintenanceId(maintenance.getId());
        BigDecimal total = BigDecimal.ZERO;

        for (MaintenanceSparePart sp : spareParts) {
            BigDecimal unitPrice = BigDecimal.valueOf(sp.getSparePart().getUnitPrice());
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(sp.getQuantity()));

            invoiceDetails.add(invoiceDetailMapper.toSparePartDetail(
                    sp.getSparePart().getName(),
                    sp.getQuantity(),
                    unitPrice,
                    lineTotal
            ));


            total = total.add(lineTotal);
        }
        return total;
    }

    private BigDecimal calculateServicesCost(Maintenance maintenance, BigDecimal sparePartsTotal, List<InvoiceDetail> invoiceDetails) {
        List<MaintenanceServiceItem> serviceItems = msiRepo.findByMaintenanceId(maintenance.getId());
        BigDecimal total = BigDecimal.ZERO;

        for (MaintenanceServiceItem si : serviceItems) {
            BigDecimal laborCost = BigDecimal.valueOf(si.getMechanicalService().getPrice())
                    .multiply(BigDecimal.valueOf(si.getEstimatedTime()));

            BigDecimal discount = sparePartsTotal.compareTo(SPARE_PARTS_DISCOUNT_THRESHOLD) > 0
                    ? laborCost.multiply(LABOR_DISCOUNT_RATE)
                    : BigDecimal.ZERO;

            BigDecimal lineTotal = laborCost.subtract(discount);

            invoiceDetails.add(invoiceDetailMapper.toServiceDetail(
                    si.getMechanicalService().getName(),
                    si.getEstimatedTime(),
                    BigDecimal.valueOf(si.getMechanicalService().getPrice()),
                    discount,
                    lineTotal
            ));

            total = total.add(laborCost);
        }
        return total;
    }

    private void validateBudget(Maintenance maintenance, BigDecimal maintenanceTotal) {
        if (maintenance.getLimitBudget() != null) {
            BigDecimal budget = BigDecimal.valueOf(maintenance.getLimitBudget());
            if (maintenanceTotal.compareTo(budget) > 0) {
                throw new IllegalArgumentException("Maintenance ID " + maintenance.getId() + " exceeds budget limit");
            }
        }
    }

    private Invoice saveInvoice(Client client, List<InvoiceDetail> details, BigDecimal subtotal, BigDecimal tax, BigDecimal total) {
        Invoice invoice = invoiceMapper.toEntity(client, details, subtotal, tax, total);
        details.forEach(d -> d.setInvoice(invoice));
        return invoiceRepo.save(invoice);
    }
}
