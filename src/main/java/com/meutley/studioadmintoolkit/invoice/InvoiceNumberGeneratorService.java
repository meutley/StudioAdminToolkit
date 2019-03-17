package com.meutley.studioadmintoolkit.invoice;

public interface InvoiceNumberGeneratorService {

    String generateInvoiceNumber(InvoiceDto invoice);
    
}