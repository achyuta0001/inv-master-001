package com.inv.invmaster001.dto.entitydto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for MaterialPriceHistory entity.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MaterialPriceHistoryDTO {

    private Long id;
    private Long materialId;
    private BigDecimal price;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}