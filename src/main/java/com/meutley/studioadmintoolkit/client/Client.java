package com.meutley.studioadmintoolkit.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meutley.studioadmintoolkit.core.model.SoftDeleteEntity;
import com.meutley.studioadmintoolkit.invoice.Invoice;
import com.meutley.studioadmintoolkit.mailingaddress.MailingAddress;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "client")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Client extends SoftDeleteEntity implements Serializable {

    private static final long serialVersionUID = -1651846186451658L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters in length")
    private String name;

    @Column(name = "email", unique = true)
    @Email(message = "Email must be a valid e-mail address")
    private String email;

    @Nullable
    @OneToOne(orphanRemoval = true)
    @Cascade(value = {CascadeType.ALL})
    private MailingAddress mailingAddress;

    @OneToMany(mappedBy = "client")
    @Cascade(value = {CascadeType.ALL})
    private List<Invoice> invoices = new ArrayList<>();

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public List<Invoice> getInvoices() {
        return this.invoices;
    }

    public MailingAddress getMailingAddress() {
        return this.mailingAddress;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setEmail(String value) {
        this.email = value;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
    
    public void setMailingAddress(MailingAddress mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
    
    public void setName(String value) {
        this.name = value;
    }

    public void addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setClient(this);
    }
    
    public void removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setClient(null);
    }

}