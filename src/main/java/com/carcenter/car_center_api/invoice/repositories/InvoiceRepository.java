package com.carcenter.car_center_api.invoice.repositories;

import com.carcenter.car_center_api.invoice.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}