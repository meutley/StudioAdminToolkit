package com.meutley.studioadmintoolkit.client;

import java.util.List;

import com.meutley.studioadmintoolkit.invoice.InvoiceDto;

public class ClientInvoiceListViewModel {

    private int clientId;
    private String clientName;
    private List<InvoiceDto> invoices;

    public int getClientId() {
        return this.clientId;
    }

    public String getClientName() {
        return this.clientName;
    }

    public List<InvoiceDto> getInvoices() {
        return this.invoices;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setInvoices(List<InvoiceDto> invoices) {
        this.invoices = invoices;
    }
    
}