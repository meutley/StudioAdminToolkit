package com.meutley.studioadmintoolkit.payment;

import java.util.Optional;

public class EditPaymentModalViewModel {

    private String clientName;
    private String invoiceNumber;
    private PaymentDto payment = new PaymentDto();
    private Optional<Integer> clientId;
    private Optional<Integer> invoiceId;
    private boolean isNew;

    public String getClientName() {
        return this.clientName;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public PaymentDto getPayment() {
        return this.payment;
    }

    public Optional<Integer> getClientId() {
        return this.clientId;
    }

    public Optional<Integer> getInvoiceId() {
        return this.invoiceId;
    }

    public boolean getIsNew() {
        return this.isNew;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }

    public void setClientId(Optional<Integer> clientId) {
        this.clientId = clientId;
    }
    
    public void setInvoiceId(Optional<Integer> invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getHeaderActionName() {
        return this.isNew
            ? "New"
            : "Edit";
    }

    public boolean hasInvoice() {
        return invoiceNumber != null && !invoiceNumber.isBlank();
    }
    
}