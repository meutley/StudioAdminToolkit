package com.meutley.studioadmintoolkit.client;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.meutley.studioadmintoolkit.mailingaddress.MailingAddressDto;

public class ClientDto implements Serializable {

    private static final long serialVersionUID = -2510402120597053246L;

    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters in length")
    private String name;

    @Email(message = "Email must be a valid e-mail address")
    private String email;

    private MailingAddressDto mailingAddress = new MailingAddressDto();

    public String getEmail() {
        return this.email;
    }
    
    public long getId() {
        return this.id;
    }

    public MailingAddressDto getMailingAddress() {
        return this.mailingAddress;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMailingAddress(MailingAddressDto mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
    
    public void setName(String value) {
        this.name = value;
    }
    
}