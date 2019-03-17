package com.meutley.studioadmintoolkit.invoice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "invoice_line_item")
@JsonIgnoreProperties({"hibernateLazyInitialization"})
public class InvoiceLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be no more than 50 characters in length")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Description must be no more than 255 characters in length")
    private String description;

    @Column(name = "is_billable")
    private boolean isBillable;

    @Column(name = "unit_price")
    @Min(value = 0, message = "Unit Price must be greater than or equal to zero")
    private BigDecimal unitPrice;

    @Column(name = "quantity")
    @NotNull
    @Min(value = 0, message = "Unit Price must be greater than or equal to zero")
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public int getId() {
        return this.id;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getIsBillable() {
        return this.isBillable;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsBillable(boolean isBillable) {
        this.isBillable = isBillable;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    
}