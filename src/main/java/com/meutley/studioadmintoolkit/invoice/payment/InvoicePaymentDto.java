package com.meutley.studioadmintoolkit.invoice.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.meutley.studioadmintoolkit.core.model.SoftDeleteEntity;
import com.meutley.studioadmintoolkit.invoice.InvoiceDto;

import org.springframework.format.annotation.DateTimeFormat;

public class InvoicePaymentDto extends SoftDeleteEntity implements Serializable {
    
    private static final long serialVersionUID = -1068901340394839912L;

    private int id;

    private InvoiceDto invoice;

    @Size(max = 255, message = "Notes cannot be more than 255 characters in length")
    private String notes;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be greater than or equal to zero")
    private BigDecimal amount;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull(message = "Date Collected is required")
    private Date dateCollected;
    
    public int getId() {
        return this.id;
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