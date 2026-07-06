package com.inv.invmaster001.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inv.invmaster001.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Find by invoice
    java.util.List<Payment> findByInvoiceId(Long invoiceId);

    // Find by date range
    java.util.List<Payment> findByPaymentDateBetween(java.time.LocalDate startDate, java.time.LocalDate endDate);
}