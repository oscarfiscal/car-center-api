package com.carcenter.car_center_api.invoice.controllers;

import com.carcenter.car_center_api.invoice.dtos.InvoiceCreateResponse;
import com.carcenter.car_center_api.invoice.services.InvoiceServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Invoice Management", description = "APIs for invoice operations")
public class InvoiceController {

    private final InvoiceServiceInterface service;

    @PostMapping("/{document}/invoices")
    @Operation(summary = "Generate invoice by document number",
            description = "Generates an invoice including all COMPLETED and non-invoiced maintenances for a client identified by document number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice generated successfully"),
            @ApiResponse(responseCode = "404", description = "Client or maintenance not found")
    })
    public ResponseEntity<InvoiceCreateResponse> generateInvoice(@PathVariable Integer document) {
        InvoiceCreateResponse response = service.generateInvoice(document);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
