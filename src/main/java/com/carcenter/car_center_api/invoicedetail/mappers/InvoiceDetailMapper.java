package com.carcenter.car_center_api.invoicedetail.mappers;

import com.carcenter.car_center_api.invoicedetail.entities.InvoiceDetail;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InvoiceDetailMapper {

    public InvoiceDetail toSparePartDetail(String sparePartName, Integer quantity, BigDecimal unitPrice, BigDecimal lineTotal) {
        return InvoiceDetail.builder()
                .description("Spare: " + sparePartName)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .discount(BigDecimal.ZERO)
                .lineTotal(lineTotal)
                .build();
    }

    public InvoiceDetail toServiceDetail(String serviceName, Integer estimatedTime, BigDecimal price, BigDecimal discount, BigDecimal lineTotal) {
        return InvoiceDetail.builder()
                .description("Service: " + serviceName)
                .quantity(estimatedTime)
                .unitPrice(price)
                .discount(discount)
                .lineTotal(lineTotal)
                .build();
    }
}
