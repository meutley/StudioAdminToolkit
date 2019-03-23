package com.meutley.studioadmintoolkit.client;

import java.math.BigDecimal;
import java.util.List;

import com.meutley.studioadmintoolkit.invoice.InvoiceLineItemDto;
import com.meutley.studioadmintoolkit.invoice.payment.InvoicePaymentDto;

public class ViewClientInvoiceViewModel extends EditClientInvoiceViewModel {

    private InvoicePaymentHistory paymentHistory = new InvoicePaymentHistory();

    public InvoicePaymentHistory getPaymentHistory() {
        return this.paymentHistory;
    }
    
    public BigDecimal getLineItemsTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceLineItemDto lineItem : getInvoice().getLineItems()) {
            if (lineItem.getIsBillable()) {
                total = lineItem.getQuantity().multiply(lineItem.getUnitPrice());
            }
        }

        return total;
    }

    public void buildPaymentHistory(List<InvoicePaymentDto> payments) {
        paymentHistory.buildPaymentHistory(getLineItemsTotal(), payments);
    }
    
}