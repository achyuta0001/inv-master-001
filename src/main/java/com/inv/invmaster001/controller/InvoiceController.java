package com.inv.invmaster001.controller;

import com.inv.invmaster001.dto.request.invoice.CreateInvoiceRequest;
import com.inv.invmaster001.dto.response.invoice.InvoicePdfResponse;
import com.inv.invmaster001.dto.response.invoice.InvoiceResponse;
import com.inv.invmaster001.security.CustomUserDetails;
import com.inv.invmaster001.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<InvoiceResponse> createInvoice(
            @Valid @RequestBody CreateInvoiceRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        InvoiceResponse response =
                invoiceService.createInvoice(
                        request,
                        currentUser.getUser()
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/{invoiceId}/pdf")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<byte[]> downloadPdf(
            @PathVariable Long invoiceId,
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        InvoicePdfResponse  response = invoiceService.downloadPdf(
                invoiceId,
                currentUser.getUser().getId()
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                response.getFileName() +
                                "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(response.getPdf());
    }
}