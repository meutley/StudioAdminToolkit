package com.meutley.studioadmintoolkit.product;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDto implements Serializable {

    private static final long serialVersionUID = -5498685715203652987L;

    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters in length")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 5, max = 150, message = "Description must be between 5 and 150 characters in length")
    private String description;

    @NotNull(message = "Is Billable is required")
    private boolean isBillable = true;

    @NotNull(message = "Unit Price is required")
    @Min(value = 0, message = "Unit Price must be greater than or equal to zero")
    private BigDecimal unitPrice = BigDecimal.ZERO;

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
    
}