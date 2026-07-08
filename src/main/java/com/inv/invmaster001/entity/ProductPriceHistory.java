package com.inv.invmaster001.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_price_history")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceHistory {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(name = "manufacturing_cost", precision = 12, scale = 2)
    private BigDecimal manufacturingCost;


    @Column(name = "selling_price", precision = 12, scale = 2)
    private BigDecimal sellingPrice;


    @Column(name = "profit_margin", precision = 5, scale = 2)
    private BigDecimal profitMargin;


    @Column(name = "effective_from")
    private LocalDate effectiveFrom;


    @Column(name = "effective_to")
    private LocalDate effectiveTo;


    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    // ============================
    // AUDIT
    // ============================

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

    }


    @PreUpdate
    public void preUpdate() {

        this.updatedAt = LocalDateTime.now();

    }
}

