package com.meutley.studioadmintoolkit.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.client.ClientDto;
import com.meutley.studioadmintoolkit.invoice.InvoiceDto;

public class PaymentListViewModel {

    private Optional<Integer> selectedClientId;
    private Optional<Integer> selectedInvoiceId;
    private List<ClientDto> clients = new ArrayList<>();
    private List<InvoiceDto> invoices = new ArrayList<>();
    private List<PaymentDto> payments = new ArrayList<>();

    public Optional<Integer> getSelectedClientId() {
        return this.selectedClientId;
    }

    public Optional<Integer> getSelectedInvoiceId() {
        return this.selectedInvoiceId;
    }

    public List<ClientDto> getClients() {
        return this.clients;
    }

    public List<InvoiceDto> getInvoices() {
        return this.invoices;
    }

    public List<PaymentDto> getPayments() {
        return this.payments;
    }

    public void setSelectedClientId(Optional<Integer> clientId) {
        this.selectedClientId = clientId;
    }

    public void setSelectedInvoiceId(Optional<Integer> invoiceId) {
        this.selectedInvoiceId = invoiceId;
    }
    
    public void setPayments(List<PaymentDto> payments) {
        this.payments = payments;
    }

    public void setClients(List<ClientDto> clients) {
        this.clients = clients;
    }

    public void setInvoices(List<InvoiceDto> invoices) {
        this.invoices = invoices;
    }

    public String getCreatePaymentLink() {
        if (this.selectedClientId.isEmpty()) {
            return "/payment/create";
        }

        String link = String.format("/payment/client/%d", this.selectedClientId.get());
        if (this.selectedInvoiceId.isPresent()) {
            link = String.format("%s/invoice/%d", link, this.selectedInvoiceId.get());
        }
        return String.format("%s/create", link);
    }
    
    public String getInvoiceNumber(InvoiceDto invoice) {
        return invoice != null
            ? invoice.getInvoiceNumber()
            : "";
    }

    public boolean isClientSelected(int clientId) {
        return this.selectedClientId.isPresent() && this.selectedClientId.get() == clientId;
    }

    public boolean isInvoiceSelected(int invoiceId) {
        return this.selectedInvoiceId.isPresent() && this.selectedInvoiceId.get() == invoiceId;
    }
    
}