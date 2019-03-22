package com.meutley.studioadmintoolkit.payment;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;
    
    public PaymentServiceImpl(
        ModelMapper modelMapper,
        PaymentRepository paymentRepository
    ) {
        this.modelMapper = modelMapper;
        this.paymentRepository = paymentRepository;
    }
    
    @Override
    public List<PaymentDto> getByClientId(int clientId) {
        List<Payment> payments = this.paymentRepository.getByClientId(clientId);
        return Arrays.asList(modelMapper.map(payments, PaymentDto[].class));
    }

    @Override
    public List<PaymentDto> getByClientInvoiceId(int clientId, int invoiceId) {
        List<Payment> payments = this.paymentRepository.getByClientInvoiceId(clientId, invoiceId);
        return Arrays.asList(modelMapper.map(payments, PaymentDto[].class));
    }

    @Override
    public PaymentDto getById(int id) {
        Optional<Payment> payment = this.paymentRepository.findById(id);
        if (payment.isEmpty()) {
            throw new EntityNotFoundException(id);
        }
        return modelMapper.map(payment.get(), PaymentDto.class);
    }

}