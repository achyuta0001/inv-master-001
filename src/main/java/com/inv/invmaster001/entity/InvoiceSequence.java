package com.inv.invmaster001.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "invoice_sequences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceSequence {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;



    private Long invoiceId;



    private String invoiceNumber;



    private LocalDate invoiceDate;



    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;



    private LocalDateTime deletedAt;



    @PrePersist
    public void prePersist(){

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

    }



    @PreUpdate
    public void preUpdate(){

        this.updatedAt = LocalDateTime.now();

    }

}