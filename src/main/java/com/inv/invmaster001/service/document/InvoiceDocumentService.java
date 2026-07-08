package com.inv.invmaster001.service.document;

import com.inv.invmaster001.dto.document.InvoiceDocumentData;
import com.inv.invmaster001.entity.Company;
import com.inv.invmaster001.entity.Customer;
import com.inv.invmaster001.entity.Invoice;
import com.inv.invmaster001.service.mapper.InvoiceDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceDocumentService {

    private final InvoiceDocumentMapper invoiceDocumentMapper;

    private final InvoicePdfService invoicePdfService;

    public byte[] generatePdf(
            Invoice invoice,
            Company company,
            Customer customer) {

        // =====================================================
        // MAP ENTITY TO DOCUMENT DTO
        // =====================================================

        InvoiceDocumentData documentData =
                invoiceDocumentMapper.map(
                        invoice,
                        company,
                        customer
                );

        // =====================================================
        // GENERATE PDF
        // =====================================================

        return invoicePdfService.generateInvoice(
                documentData
        );
    }

}