package com.carcenter.car_center_api.invoice.services;

import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse;

public interface InvoiceServiceInterface {

    /**
     * Generates an invoice based on the client's document number.
     *
     * @param document the document number of the client
     * @return the generated invoice response
     */
    InvoiceCreateResponse generateInvoice(Integer document);
}
