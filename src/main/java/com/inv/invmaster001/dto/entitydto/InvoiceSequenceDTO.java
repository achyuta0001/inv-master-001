package com.inv.invmaster001.dto.entitydto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for InvoiceSequence entity.
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
public class InvoiceSequenceDTO {

    private Long id;
    private Long companyId;
    private Integer year;
    private String prefix;
    private Integer nextNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}