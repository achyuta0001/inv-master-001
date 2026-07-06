package com.inv.invmaster001.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inv.invmaster001.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Find by invoice number (unique)
    Invoice findByInvoiceNumber(String invoiceNumber);

    // Find by company
    java.util.List<Invoice> findByCompanyId(Long companyId);

    // Find by createdBy (user)
    java.util.List<Invoice> findByCreatedById(Long userId);

    // Find by date range
    java.util.List<Invoice> findByInvoiceDateBetween(java.time.LocalDate startDate, java.time.LocalDate endDate);

    // Find by status
    java.util.List<Invoice> findByStatus(String status);
}