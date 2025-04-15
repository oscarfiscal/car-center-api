package com.carcenter.car_center_api.invoice.services.impl;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse.ClientInfo;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse.InvoiceDetailInfo;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse.MechanicInfo;    // << Import añadido
import com.carcenter.car_center_api.invoice.entities.Invoice;
import com.carcenter.car_center_api.invoice.repositories.InvoiceRepository;
import com.carcenter.car_center_api.invoice.services.InvoiceServiceInterface;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceServiceInterface {

    private final ClientRepository clientRepo;
    private final MaintenanceRepository maintenanceRepo;
    private final MaintenanceSparePartRepository mspRepo;
    private final MaintenanceServiceItemRepository msiRepo;
    private final InvoiceRepository invoiceRepo;

    // Constantes en BigDecimal para manejo de dinero en COP
    private static final BigDecimal SPARE_PARTS_DISCOUNT_THRESHOLD = new BigDecimal("3000000"); // 3,000,000 COP
    private static final BigDecimal LABOR_DISCOUNT_RATE = new BigDecimal("0.5");                // 50% descuento
    private static final BigDecimal TAX_RATE = new BigDecimal("0.19");                          // 19% IVA

    @Override
    public InvoiceCreateResponse generateInvoice(Long clientId) {
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // 1. Obtener todos los mantenimientos COMPLETED y no facturados para el cliente
        List<Maintenance> maintenancesToInvoice = maintenanceRepo.findAll().stream()
                .filter(m -> m.getClient().getId().equals(clientId))
                .filter(m -> m.getStatus() == MaintenanceStatus.COMPLETED && !m.isInvoiced())
                .toList();

        if (maintenancesToInvoice.isEmpty()) {
            throw new IllegalArgumentException("No completed maintenances to invoice");
        }

        List<InvoiceDetail> details = new ArrayList<>();
        BigDecimal invoiceSubtotal = BigDecimal.ZERO;

        for (Maintenance m : maintenancesToInvoice) {
            // --- Desglose de repuestos ---
            List<MaintenanceSparePart> spareParts = mspRepo.findByMaintenanceId(m.getId());
            BigDecimal sparePartsTotal = BigDecimal.ZERO;
            for (MaintenanceSparePart sp : spareParts) {
                BigDecimal unitPrice = BigDecimal.valueOf(sp.getSparePart().getUnitPrice());
                int qty = sp.getQuantity();
                BigDecimal line = unitPrice.multiply(BigDecimal.valueOf(qty));
                InvoiceDetail detail = InvoiceDetail.builder()
                        .description("Spare: " + sp.getSparePart().getName())
                        .quantity(qty)
                        .unitPrice(unitPrice.doubleValue())
                        .discount(0.0)
                        .lineTotal(line.doubleValue())
                        .build();
                details.add(detail);
                sparePartsTotal = sparePartsTotal.add(line);
            }

            // --- Desglose de servicios (mano de obra) ---
            List<MaintenanceServiceItem> serviceItems = msiRepo.findByMaintenanceId(m.getId());
            BigDecimal servicesTotal = BigDecimal.ZERO;
            for (MaintenanceServiceItem si : serviceItems) {
                BigDecimal laborCost = BigDecimal.valueOf(si.getMechanicalService().getPrice())
                        .multiply(BigDecimal.valueOf(si.getEstimatedTime()));
                servicesTotal = servicesTotal.add(laborCost);
            }
            // Calcular descuento en mano de obra si repuestos > 3,000,000 COP
            BigDecimal serviceDiscount = sparePartsTotal.compareTo(SPARE_PARTS_DISCOUNT_THRESHOLD) > 0
                    ? servicesTotal.multiply(LABOR_DISCOUNT_RATE)
                    : BigDecimal.ZERO;

            for (MaintenanceServiceItem si : serviceItems) {
                BigDecimal laborCost = BigDecimal.valueOf(si.getMechanicalService().getPrice())
                        .multiply(BigDecimal.valueOf(si.getEstimatedTime()));
                BigDecimal discount = sparePartsTotal.compareTo(SPARE_PARTS_DISCOUNT_THRESHOLD) > 0
                        ? laborCost.multiply(LABOR_DISCOUNT_RATE)
                        : BigDecimal.ZERO;
                BigDecimal line = laborCost.subtract(discount);
                InvoiceDetail detail = InvoiceDetail.builder()
                        .description("Service: " + si.getMechanicalService().getName())
                        .quantity(si.getEstimatedTime())
                        .unitPrice(si.getMechanicalService().getPrice())
                        .discount(discount.doubleValue())
                        .lineTotal(line.doubleValue())
                        .build();
                details.add(detail);
            }

            // Validar presupuesto del mantenimiento
            BigDecimal maintenanceTotal = sparePartsTotal.add(servicesTotal).subtract(serviceDiscount);
            if (m.getLimitBudget() != null) {
                BigDecimal maintenanceBudget = BigDecimal.valueOf(m.getLimitBudget());
                if (maintenanceTotal.compareTo(maintenanceBudget) > 0) {
                    throw new IllegalArgumentException("Maintenance ID " + m.getId() + " exceeds its budget limit");
                }
            }

            // Marcar mantenimiento como facturado
            m.setInvoiced(true);
            invoiceSubtotal = invoiceSubtotal.add(maintenanceTotal);
        }

        // 2. Calcular IVA y total
        BigDecimal tax = invoiceSubtotal.multiply(TAX_RATE);
        BigDecimal invoiceTotal = invoiceSubtotal.add(tax);

        // 3. Construir y guardar la factura
        Invoice invoice = Invoice.builder()
                .client(client)
                .createdAt(LocalDateTime.now())
                .details(details)
                .subtotal(invoiceSubtotal.doubleValue())
                .tax(tax.doubleValue())
                .total(invoiceTotal.doubleValue())
                .build();

        details.forEach(d -> d.setInvoice(invoice));
        invoiceRepo.save(invoice);

        // 4. Mapear detalles a DTOs
        List<InvoiceDetailInfo> detailInfos = details.stream()
                .map(d -> new InvoiceDetailInfo(
                        d.getDescription(),
                        d.getQuantity(),
                        d.getUnitPrice(),
                        d.getDiscount(),
                        d.getLineTotal()
                ))
                .collect(Collectors.toList());

        // 5. Mapear datos de cliente
        ClientInfo ci = new ClientInfo(
                client.getId(),
                client.getDocumentType(),
                client.getDocument(),
                client.getFirstName(),
                client.getSecondName(),
                client.getFirstLastName(),
                client.getSecondLastName(),
                client.getCellphone(),
                client.getAddress(),
                client.getEmail()
        );

        // ========recolectar datos de mecánicos ========
        List<MechanicInfo> mechInfos = maintenancesToInvoice.stream()
                .map(Maintenance::getMechanic)
                .filter(Objects::nonNull)
                .map(mech -> new MechanicInfo(
                        mech.getId(),
                        mech.getFirstName(),
                        mech.getSecondName(),
                        mech.getFirstLastName(),
                        mech.getSecondLastName(),
                        mech.getDocumentType(),
                        mech.getDocument(),
                        mech.getCellphone(),
                        mech.getAddress(),
                        mech.getEmail(),
                        mech.getStatus().name()
                ))
                .distinct()
                .collect(Collectors.toList());


        return InvoiceCreateResponse.builder()
                .id(invoice.getId())
                .createdAt(invoice.getCreatedAt())
                .client(ci)
                .mechanics(mechInfos)
                .details(detailInfos)
                .subtotal(invoice.getSubtotal())
                .tax(invoice.getTax())
                .total(invoice.getTotal())
                .build();
    }
}
