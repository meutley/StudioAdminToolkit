package com.meutley.studioadmintoolkit.invoice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meutley.studioadmintoolkit.client.Client;
import com.meutley.studioadmintoolkit.core.model.SoftDeleteEntity;

@Entity
@Table(name = "invoice")
@JsonIgnoreProperties({"hibernateLazyInitialization"})
public class Invoice extends SoftDeleteEntity implements Serializable {
    
    private static final long serialVersionUID = -3456978100259854930L;

    @Column(name = "invoice_number")
    @Size(max = 20)
    private String invoiceNumber;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "invoice", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<InvoiceLineItem> lineItems = new ArrayList<>();
    
    public Client getClient() {
        return this.client;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public List<InvoiceLineItem> getLineItems() {
        return this.lineItems;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setLineItems(List<InvoiceLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void addLineItem(InvoiceLineItem lineItem) {
        this.lineItems.add(lineItem);
        lineItem.setInvoice(this);
    }

    public void removeLineItem(InvoiceLineItem lineItem) {
        this.lineItems.remove(lineItem);
        lineItem.setInvoice(null);
    }
    
}