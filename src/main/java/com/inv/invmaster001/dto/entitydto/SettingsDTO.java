package com.inv.invmaster001.dto.entitydto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Settings entity.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SettingsDTO {

    private Long id;
    private Long companyId;
    private BigDecimal gstPercentage;
    private BigDecimal defaultProfitMargin;
    private String currency;
    private String invoicePrefix;
    private String financialYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}