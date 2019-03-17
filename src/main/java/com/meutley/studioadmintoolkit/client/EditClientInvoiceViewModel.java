package com.meutley.studioadmintoolkit.client;

import java.util.ArrayList;
import java.util.List;

import com.meutley.studioadmintoolkit.invoice.InvoiceDto;
import com.meutley.studioadmintoolkit.mailingaddress.MailingAddressDto;

public class EditClientInvoiceViewModel {

    private ClientDto client;
    private InvoiceDto invoice;

    public ClientDto getClient() {
        return this.client;
    }

    public InvoiceDto getInvoice() {
        return this.invoice;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }

    public List<String> getMailingAddressLines() {
        MailingAddressDto mailingAddress = this.client.getMailingAddress();
        if (mailingAddress == null || mailingAddress.getId() == 0) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        result.add(this.getClient().getName());
        result.add(mailingAddress.getLine1());
        if (mailingAddress.getLine2() != null && !mailingAddress.getLine2().isEmpty()) {
            result.add(mailingAddress.getLine2());
        }
        if (mailingAddress.getLine3() != null && !mailingAddress.getLine3().isEmpty()) {
            result.add(mailingAddress.getLine3());
        }
        result.add(
            String.format("%s, %s %s",
                mailingAddress.getCity(),
                mailingAddress.getState(),
                mailingAddress.getZipCode()));

        return result;
    }
    
}