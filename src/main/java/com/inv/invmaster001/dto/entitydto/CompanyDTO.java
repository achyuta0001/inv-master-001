package com.inv.invmaster001.dto.entitydto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Company entity.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyDTO {

    private Long id;
    private String companyName;
    private String gstNumber;
    private String phone;
    private String email;
    private String address;
    private String bankName;
    private String accountNumber;
    private String ifsc;
    private String upiId;
    private String logoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}