package com.meutley.studioadmintoolkit.client;

import java.math.BigDecimal;

import com.meutley.studioadmintoolkit.invoice.payment.InvoicePaymentDto;

public class InvoicePaymentHistoryRow {

    private InvoicePaymentDto payment;
    private BigDecimal balance;

    public InvoicePaymentDto getPayment() {
        return this.payment;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setPayment(InvoicePaymentDto payment) {
        this.payment = payment;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
}