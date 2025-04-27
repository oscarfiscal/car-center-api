package com.carcenter.car_center_api.invoice.mappers;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse.ClientInfo;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse.InvoiceDetailInfo;
import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse.MechanicInfo;
import com.carcenter.car_center_api.invoice.entities.Invoice;
import com.carcenter.car_center_api.invoicedetail.entities.InvoiceDetail;
import com.carcenter.car_center_api.mechanic.entities.Mechanic;
import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    public ClientInfo toClientInfo(Client client) {
        return ClientInfo.builder()
                .id(client.getId())
                .documentType(client.getDocumentType())
                .document(client.getDocument())
                .firstName(client.getFirstName())
                .secondName(client.getSecondName())
                .firstLastName(client.getFirstLastName())
                .secondLastName(client.getSecondLastName())
                .cellphone(client.getCellphone())
                .address(client.getAddress())
                .email(client.getEmail())
                .build();
    }

    public InvoiceDetailInfo toInvoiceDetailInfo(InvoiceDetail detail) {
        return InvoiceDetailInfo.builder()
                .description(detail.getDescription())
                .quantity(detail.getQuantity())
                .unitPrice(detail.getUnitPrice())
                .discount(detail.getDiscount())
                .lineTotal(detail.getLineTotal())
                .build();
    }

    public MechanicInfo toMechanicInfo(Mechanic mechanic) {
        return MechanicInfo.builder()
                .id(mechanic.getId())
                .firstName(mechanic.getFirstName())
                .secondName(mechanic.getSecondName())
                .firstLastName(mechanic.getFirstLastName())
                .secondLastName(mechanic.getSecondLastName())
                .documentType(mechanic.getDocumentType())
                .document(mechanic.getDocument())
                .cellphone(mechanic.getCellphone())
                .address(mechanic.getAddress())
                .email(mechanic.getEmail())
                .status(mechanic.getStatus().name())
                .build();
    }

    public Invoice toEntity(Client client, List<InvoiceDetail> details, BigDecimal subtotal, BigDecimal tax, BigDecimal total) {
        return Invoice.builder()
                .client(client)
                .createdAt(LocalDateTime.now())
                .details(details)
                .subtotal(subtotal)
                .tax(tax)
                .total(total)
                .build();
    }

    public InvoiceCreateResponse toResponse(Client client, List<Maintenance> maintenances, Invoice invoice, List<InvoiceDetail> details) {
        List<InvoiceDetailInfo> detailInfos = details.stream()
                .map(this::toInvoiceDetailInfo)
                .collect(Collectors.toList());

        ClientInfo clientInfo = toClientInfo(client);

        List<MechanicInfo> mechanicInfos = maintenances.stream()
                .map(Maintenance::getMechanic)
                .filter(Objects::nonNull)
                .map(this::toMechanicInfo)
                .distinct()
                .collect(Collectors.toList());

        return InvoiceCreateResponse.builder()
                .id(invoice.getId())
                .createdAt(invoice.getCreatedAt())
                .client(clientInfo)
                .mechanics(mechanicInfos)
                .details(detailInfos)
                .subtotal(invoice.getSubtotal())
                .tax(invoice.getTax())
                .total(invoice.getTotal())
                .build();
    }
}
