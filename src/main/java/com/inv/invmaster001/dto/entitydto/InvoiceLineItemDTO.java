package com.inv.invmaster001.dto.entitydto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for InvoiceLineItem entity.
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
public class InvoiceLineItemDTO {

    private Long id;
    private Long invoiceId;
    private Long productId;
    private String productName;
    private String unit;
    private BigDecimal unitPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}