package com.meutley.studioadmintoolkit.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.meutley.studioadmintoolkit.invoice.payment.InvoicePaymentDto;

public class InvoicePaymentHistory {

    private BigDecimal remainingBalance = BigDecimal.ZERO;
    private List<InvoicePaymentHistoryRow> paymentHistoryRows = new ArrayList<>();

    public BigDecimal getRemainingBalance() {
        return this.remainingBalance;
    }

    public List<InvoicePaymentHistoryRow> getPaymentHistoryRows() {
        return this.paymentHistoryRows;
    }
    
    public void buildPaymentHistory(BigDecimal invoiceTotal, List<InvoicePaymentDto> payments) {
        if (payments.size() == 0) {
            this.remainingBalance = invoiceTotal;
            return;
        }
        
        BigDecimal balance = invoiceTotal;
        for (InvoicePaymentDto payment : payments) {
            balance = balance.subtract(payment.getAmount());

            InvoicePaymentHistoryRow row = new InvoicePaymentHistoryRow();
            row.setPayment(payment);
            row.setBalance(balance);
            paymentHistoryRows.add(row);
        }
        Comparator<InvoicePaymentHistoryRow> comparator = Comparator.comparing(row -> row.getPayment().getDateCollected(), Collections.reverseOrder());
        comparator.thenComparing(row -> row.getPayment().getId());
        this.paymentHistoryRows.sort(comparator.reversed());
        Collections.reverse(this.paymentHistoryRows);
        this.remainingBalance = invoiceTotal.subtract(balance);
    }
    
}