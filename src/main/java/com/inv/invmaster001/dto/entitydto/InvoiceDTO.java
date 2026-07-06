package com.inv.invmaster001.dto.entitydto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Invoice entity.
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceDTO {

    private Long id;
    private String invoiceNumber;
    private Long companyId;
    private Long createdById;
    private LocalDate invoiceDate;
    private BigDecimal subtotal;
    private BigDecimal gst;
    private BigDecimal discount;
    private BigDecimal grandTotal;
    private String status;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}