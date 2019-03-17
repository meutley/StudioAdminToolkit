package com.meutley.studioadmintoolkit.invoice;

import java.util.List;

public interface InvoiceService {

    InvoiceDto create(InvoiceDto invoice);
    void deleteLineItem(int invoiceId, int lineItemId);
    List<InvoiceDto> getByClientId(int clientId);
    InvoiceDto getById(int id);
    
}