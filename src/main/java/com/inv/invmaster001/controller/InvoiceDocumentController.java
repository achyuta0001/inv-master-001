package com.inv.invmaster001.controller;

import com.inv.invmaster001.dto.document.InvoiceDocumentData;
import com.inv.invmaster001.dto.document.InvoiceDocumentItem;
import com.inv.invmaster001.service.document.InvoicePdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class InvoiceDocumentController {

    private final InvoicePdfService pdfService;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> testPdf() {

        InvoiceDocumentData data = InvoiceDocumentData.builder()

                .companyName("ABC Pvt Ltd")
                .companyAddress("Bangalore")
                .companyPhone("9876543210")
                .companyEmail("abc@test.com")
                .companyGst("29ABCDE1234F1Z5")

                .customerName("John")
                .customerAddress("Mysore")
                .customerPhone("9999999999")
                .customerEmail("john@test.com")
                .customerGst("29AAAAA1111A1Z5")

                .invoiceNumber("INV-00001")
                .invoiceDate(LocalDate.now())
                .poNumber("PO-1001")

                .subtotal(new BigDecimal("1000"))
                .cgst(new BigDecimal("90"))
                .sgst(new BigDecimal("90"))
                .discount(BigDecimal.ZERO)
                .grandTotal(new BigDecimal("1180"))

                .bankName("HDFC")
                .accountNumber("123456789")
                .ifsc("HDFC0001")

                .items(List.of(
                        InvoiceDocumentItem.builder()
                                .serialNumber(1)
                                .productName("Laptop")
                                .quantity(new BigDecimal("2"))
                                .unitPrice(new BigDecimal("500"))
                                .totalPrice(new BigDecimal("1000"))
                                .build()
                ))

                .remarks("Thank you")

                .build();

        byte[] pdf =
                pdfService.generateInvoice(data);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice.pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
