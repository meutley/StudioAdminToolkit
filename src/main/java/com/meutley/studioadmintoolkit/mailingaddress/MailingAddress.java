package com.meutley.studioadmintoolkit.mailingaddress;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meutley.studioadmintoolkit.core.model.BaseEntity;

@Entity
@Table(name = "mailing_address")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class MailingAddress extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5291825889119394600L;

    @Column(name = "line_1")
    @NotBlank(message = "Line 1 cannot be blank")
    @Size(min = 5, max = 255, message = "Line 1 must be between 5 and 255 characters in length")
    private String line1;

    @Column(name = "line_2")
    @Size(max = 100, message = "Line 2 must be no more than 100 characters in length")
    private String line2;

    @Column(name = "line_3")
    @Size(max = 100, message = "Line 3 must be no more than 100 characters in length")
    private String line3;

    @Column(name = "city")
    @NotBlank(message = "City cannot be blank")
    @Size(max = 255, message = "City must be between 1 and 255 characters in length")
    private String city;

    @Column(name = "state")
    @NotBlank(message = "State cannot be blank")
    @Size(max = 50, message = "State must be between 1 and 50 characters in length")
    private String state;

    @Column(name = "zip_code")
    @NotNull(message = "Zip Code is required")
    private int zipCode;

    public String getLine1() {
        return this.line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public String getLine3() {
        return this.line3;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public int getZipCode() {
        return this.zipCode;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
    
}