package com.meutley.studioadmintoolkit.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.meutley.studioadmintoolkit.client.ClientDto;
import com.meutley.studioadmintoolkit.core.model.SoftDeleteEntity;
import com.meutley.studioadmintoolkit.invoice.InvoiceDto;

public class PaymentDto extends SoftDeleteEntity implements Serializable {
    
    private static final long serialVersionUID = -1068901340394839912L;

    private int id;

    private ClientDto client;

    private InvoiceDto invoice;

    @Size(max = 255, message = "Notes cannot be more than 255 characters in length")
    private String notes;

    @NotNull
    @Min(value = 0, message = "Amount must be greater than or equal to zero")
    private BigDecimal amount;

    @NotNull
    private Date dateCollected;
    
    public int getId() {
        return this.id;
    }

    public ClientDto getClient() {
        return this.client;
    }

    public InvoiceDto getInvoice() {
        return this.invoice;
    }

    public String getNotes() {
        return this.notes;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Date getDateCollected() {
        return this.dateCollected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDateCollected(Date dateCollected) {
        this.dateCollected = dateCollected;
    }
    
}