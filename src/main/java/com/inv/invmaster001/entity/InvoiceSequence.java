package com.inv.invmaster001.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;

/**
 * InvoiceSequence entity mapped from the invoice_sequences table.
 */
@Entity
@Table(name = "invoice_sequences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"company_id", "year"}))
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceSequence {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "prefix", length = 20)
    private String prefix;

    @Column(name = "next_number", nullable = false)
    private Integer nextNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}