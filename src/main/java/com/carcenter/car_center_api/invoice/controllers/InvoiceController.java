package com.carcenter.car_center_api.invoice.controllers;

import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse;
import com.carcenter.car_center_api.invoice.services.InvoiceServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceServiceInterface service;

    /**
     * Genera la factura para un cliente dado su identificación.
     * La factura incluye el cobro de todos los mantenimientos en estado COMPLETED y no facturados.
     * Ejemplo: POST /api/clients/{clientId}/invoices
     */
    @PostMapping("/clients/{clientId}/invoices")
    public ResponseEntity<InvoiceCreateResponse> generateInvoice(@PathVariable Long clientId) {
        InvoiceCreateResponse response = service.generateInvoice(clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
