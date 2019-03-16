package com.meutley.studioadmintoolkit.client;

import com.meutley.studioadmintoolkit.invoice.InvoiceDto;

public class EditClientInvoiceViewModel {

    private ClientDto client;
    private InvoiceDto invoice;

    public ClientDto getClient() {
        return this.client;
    }

    public InvoiceDto getInvoice() {
        return this.invoice;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }
    
}