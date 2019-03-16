package com.meutley.studioadmintoolkit.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.meutley.studioadmintoolkit.invoice.InvoiceDto;
import com.meutley.studioadmintoolkit.mailingaddress.MailingAddressDto;

public class ClientDto implements Serializable {

    private static final long serialVersionUID = -2510402120597053246L;

    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters in length")
    private String name;

    @Email(message = "Email must be a valid e-mail address")
    private String email;

    private boolean isActive;

    private List<InvoiceDto> invoices = new ArrayList<>();
    
    private MailingAddressDto mailingAddress = new MailingAddressDto();

    public String getEmail() {
        return this.email;
    }
    
    public long getId() {
        return this.id;
    }

    public List<InvoiceDto> getInvoices() {
        return this.invoices;
    }

    public MailingAddressDto getMailingAddress() {
        return this.mailingAddress;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInvoices(List<InvoiceDto> invoices) {
        this.invoices = invoices;
    }

    public void setMailingAddress(MailingAddressDto mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
    
    public void setName(String value) {
        this.name = value;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void addInvoice(InvoiceDto invoice) {
        this.invoices.add(invoice);
        invoice.setClient(this);
    }
    
    public void removeInvoice(InvoiceDto invoice) {
        this.invoices.remove(invoice);
        invoice.setClient(null);
    }
    
}