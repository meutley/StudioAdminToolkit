package com.meutley.studioadmintoolkit.mailingaddress;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MailingAddressDto implements Serializable {

    private static final long serialVersionUID = -5291825889119394600L;

    private int id;

    @NotBlank(message = "Line 1 cannot be blank")
    @Size(min = 5, max = 255, message = "Line 1 must be between 5 and 255 characters in length")
    private String line1;

    @Size(max = 100, message = "Line 2 must be no more than 100 characters in length")
    private String line2;

    @Size(max = 100, message = "Line 3 must be no more than 100 characters in length")
    private String line3;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 255, message = "City must be between 1 and 255 characters in length")
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Size(max = 50, message = "State must be between 1 and 50 characters in length")
    private String state;

    @NotNull(message = "Zip Code is required")
    private Integer zipCode;

    public int getId() {
        return this.id;
    }

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

    public Integer getZipCode() {
        return this.zipCode;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
    
}