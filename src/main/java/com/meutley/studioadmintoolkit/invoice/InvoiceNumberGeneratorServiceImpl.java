package com.meutley.studioadmintoolkit.invoice;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.meutley.studioadmintoolkit.core.service.RandomNumberService;

import org.springframework.stereotype.Service;

@Service
public class InvoiceNumberGeneratorServiceImpl implements InvoiceNumberGeneratorService {

    private static final String PREFIX = "SATK";
    private static final String CONTROL_CODE = "IV";
    private static final int CLIENT_ID_PAD = 6;

    private final RandomNumberService randomNumberService;

    public InvoiceNumberGeneratorServiceImpl(
        RandomNumberService randomNumberService
    ) {
        this.randomNumberService = randomNumberService;
    }
    
    @Override
    public String generateInvoiceNumber(InvoiceDto invoice) {
        String paddedClientId = String.format(
            "%" + CLIENT_ID_PAD + "s",
            invoice.getClient().getId()).replace(' ', '0');

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
        String datePart = dateFormat.format(new Date());

        int randomNumber = this.randomNumberService.generate(10, 99);

        return String.format(
            "%s-%s-%d%s%s",
            PREFIX,
            CONTROL_CODE,
            randomNumber,
            paddedClientId,
            datePart);
    }

}