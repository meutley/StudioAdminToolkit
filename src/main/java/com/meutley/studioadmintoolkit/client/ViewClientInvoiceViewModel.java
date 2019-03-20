package com.meutley.studioadmintoolkit.client;

import java.math.BigDecimal;

import com.meutley.studioadmintoolkit.invoice.InvoiceLineItemDto;

public class ViewClientInvoiceViewModel extends EditClientInvoiceViewModel {

    public BigDecimal getLineItemsTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceLineItemDto lineItem : getInvoice().getLineItems()) {
            if (lineItem.getIsBillable()) {
                total = lineItem.getQuantity().multiply(lineItem.getUnitPrice());
            }
        }

        return total;
    }
    
}