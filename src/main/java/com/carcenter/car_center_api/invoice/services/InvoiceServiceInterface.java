package com.carcenter.car_center_api.invoice.services;

import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse;

public interface InvoiceServiceInterface {
    InvoiceCreateResponse generateInvoice(Long clientId);
}
