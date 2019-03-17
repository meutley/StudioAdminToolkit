package com.meutley.studioadmintoolkit.invoice;

import org.springframework.stereotype.Service;

@Service
public class InvoiceNumberGeneratorServiceImpl implements InvoiceNumberGeneratorService {

    private static final String PREFIX = "SATK";
    private static final String CONTROL_CODE = "IV";
    private static final int CLIENT_ID_PAD = 6;
    
    @Override
    public String generateInvoiceNumber(InvoiceDto invoice) {
        String paddedClientId = String.format(
            "%" + CLIENT_ID_PAD + "s",
            invoice.getClient().getId()).replace(' ', '0');

        return String.format(
            "%s-%s-%s",
            PREFIX,
            CONTROL_CODE,
            paddedClientId);
    }

}