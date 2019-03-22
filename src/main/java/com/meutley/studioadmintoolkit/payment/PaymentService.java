package com.meutley.studioadmintoolkit.payment;

import java.util.List;

public interface PaymentService {

    List<PaymentDto> getByClientId(int clientId);
    List<PaymentDto> getByClientInvoiceId(int clientId, int invoiceId);
    PaymentDto getById(int id);
    
}