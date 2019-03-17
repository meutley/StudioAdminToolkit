package com.meutley.studioadmintoolkit.invoice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.meutley.studioadmintoolkit.client.ClientDto;

public class InvoiceDto implements Serializable {
    
    private static final long serialVersionUID = -1200025478911059331L;

    private int id;

    @Size(max = 26)
    private String invoiceNumber;
    
    private ClientDto client;

    @Valid
    private List<InvoiceLineItemDto> lineItems = new ArrayList<>();

    public int getId() {
        return this.id;
    }
    
    public ClientDto getClient() {
        return this.client;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public List<InvoiceLineItemDto> getLineItems() {
        return this.lineItems;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceLineItems(List<InvoiceLineItemDto> lineItems) {
        this.lineItems = lineItems;
    }
    
}