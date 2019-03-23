package com.meutley.studioadmintoolkit.invoice.payment;

import java.util.List;

public interface InvoicePaymentService {

    InvoicePaymentDto create(InvoicePaymentDto payment);
    InvoicePaymentDto getById(int id);
    List<InvoicePaymentDto> getByInvoiceId(int invoiceId);
    List<InvoicePaymentDto> getMostRecentPayments(int invoiceId);
    
}