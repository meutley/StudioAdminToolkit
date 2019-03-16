package com.meutley.studioadmintoolkit.invoice;

import java.util.List;

public interface InvoiceService {

    InvoiceDto create(InvoiceDto invoice);
    List<InvoiceDto> getByClientId(int clientId);
    InvoiceDto getById(int id);
    
}