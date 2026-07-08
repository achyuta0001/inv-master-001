package com.inv.invmaster001.controller;

import com.inv.invmaster001.dto.request.invoice.CreateInvoiceRequest;
import com.inv.invmaster001.dto.response.invoice.InvoiceResponse;
import com.inv.invmaster001.security.CustomUserDetails;
import com.inv.invmaster001.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
}