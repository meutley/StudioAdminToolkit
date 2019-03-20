package com.meutley.studioadmintoolkit.product;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meutley.studioadmintoolkit.core.model.SoftDeleteEntity;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Product extends SoftDeleteEntity implements Serializable {

    private static final long serialVersionUID = -7315566214925782371L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters in length")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 5, max = 150, message = "Description must be between 5 and 150 characters in length")
    private String description;

    @Column(name = "is_billable")
    @NotNull(message = "Is Billable is required")
    private boolean isBillable;

    @Column(name = "unit_price")
    @NotNull(message = "Unit Price is required")
    @Min(value = 0, message = "Unit Price must be greater than or equal to zero")
    private BigDecimal unitPrice;

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