package com.meutley.studioadmintoolkit.invoice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.meutley.studioadmintoolkit.client.ClientDto;
import com.meutley.studioadmintoolkit.invoice.payment.InvoicePaymentDto;

public class InvoiceDto implements Serializable {
    
    private static final long serialVersionUID = -1200025478911059331L;

    private int id;

    @Size(max = 20)
    private String invoiceNumber;
    
    private ClientDto client;

    @Valid
    private List<InvoiceLineItemDto> lineItems = new ArrayList<>();

    private List<InvoicePaymentDto> payments = new ArrayList<>();

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

    public List<InvoicePaymentDto> getPayments() {
        return this.payments;
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

    public void setLineItems(List<InvoiceLineItemDto> lineItems) {
        this.lineItems = lineItems;
    }

    public void setPayments(List<InvoicePaymentDto> payments) {
        this.payments = payments;
    }
    
}