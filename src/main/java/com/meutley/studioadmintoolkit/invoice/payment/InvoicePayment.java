package com.meutley.studioadmintoolkit.invoice.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meutley.studioadmintoolkit.core.model.SoftDeleteEntity;
import com.meutley.studioadmintoolkit.invoice.Invoice;

@Entity
@Table(name = "invoice_payment")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class InvoicePayment extends SoftDeleteEntity implements Serializable {

    private static final long serialVersionUID = 6958668831277452736L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(name = "notes")
    @Size(max = 255, message = "Notes cannot be more than 255 characters in length")
    private String notes;

    @Column(name = "amount")
    @NotNull
    @Min(value = 0, message = "Amount must be greater than or equal to zero")
    private BigDecimal amount;

    @Column(name = "date_collected")
    @NotNull
    @Basic
    @Temporal(TemporalType.DATE)
    private Date dateCollected;
    
    public int getId() {
        return this.id;
    }

    public Invoice getInvoice() {
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

    public void setInvoice(Invoice invoice) {
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