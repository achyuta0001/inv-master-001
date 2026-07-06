package com.inv.invmaster001.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

/**
 * Invoice entity mapped from the invoices table.
 */
@Entity
@Table(name = "invoices", uniqueConstraints = @UniqueConstraint(columnNames = "invoice_number"))
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "invoice_number", length = 50, nullable = false, unique = true)
    private String invoiceNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "gst", precision = 12, scale = 2, nullable = false)
    private BigDecimal gst;

    @Column(name = "discount", precision = 12, scale = 2, nullable = false)
    private BigDecimal discount;

    @Column(name = "grand_total", precision = 12, scale = 2, nullable = false)
    private BigDecimal grandTotal;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}