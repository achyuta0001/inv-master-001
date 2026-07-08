package com.inv.invmaster001.dto.response.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePdfResponse {

    private String fileName;

    private byte[] pdf;

}
