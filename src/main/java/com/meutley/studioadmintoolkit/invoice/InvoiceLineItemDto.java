package com.meutley.studioadmintoolkit.invoice;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class InvoiceLineItemDto {

    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be no more than 50 characters in length")
    private String name;

    @Size(max = 255, message = "Description must be no more than 255 characters in length")
    private String description;
    
    private boolean isBillable;

    @Min(value = 0, message = "Unit Price must be greater than or equal to zero")
    private BigDecimal unitPrice;

    @Min(value = 0, message = "Unit Price must be greater than or equal to zero")
    private BigDecimal quantity;

    public int getId() {
        return this.id;
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