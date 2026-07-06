package com.inv.invmaster001.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Settings entity mapped from the settings table.
 */
@Entity
@Table(name = "settings")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false, unique = true)
    private Company company;

    @Column(name = "gst_percentage", precision = 5, scale = 2)
    private BigDecimal gstPercentage;

    @Column(name = "default_profit_margin", precision = 5, scale = 2)
    private BigDecimal defaultProfitMargin;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "invoice_prefix", length = 20)
    private String invoicePrefix;

    @Column(name = "financial_year", length = 20)
    private String financialYear;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}