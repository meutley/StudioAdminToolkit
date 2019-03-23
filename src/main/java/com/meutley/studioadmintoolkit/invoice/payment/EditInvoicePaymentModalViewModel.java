package com.meutley.studioadmintoolkit.invoice.payment;

import javax.validation.Valid;

public class EditInvoicePaymentModalViewModel {

    private String clientName;
    private String invoiceNumber;
    @Valid
    private InvoicePaymentDto payment = new InvoicePaymentDto();
    private int invoiceId;
    private boolean isNew;

    public String getClientName() {
        return this.clientName;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public InvoicePaymentDto getPayment() {
        return this.payment;
    }

    public int getInvoiceId() {
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
    
    public void setPayment(InvoicePaymentDto payment) {
        this.payment = payment;
    }
    
    public void setInvoiceId(int invoiceId) {
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