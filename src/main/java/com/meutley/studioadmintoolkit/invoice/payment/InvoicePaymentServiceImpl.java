package com.meutley.studioadmintoolkit.invoice.payment;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class InvoicePaymentServiceImpl implements InvoicePaymentService {

    private final ModelMapper modelMapper;
    private final InvoicePaymentRepository invoicePaymentRepository;

    public InvoicePaymentServiceImpl(
        ModelMapper modelMapper,
        InvoicePaymentRepository invoicePaymentRepository
    ) {
        this.modelMapper = modelMapper;
        this.invoicePaymentRepository = invoicePaymentRepository;
    }

    public InvoicePaymentDto create(InvoicePaymentDto payment) {
        InvoicePayment entity = this.invoicePaymentRepository.save(modelMapper.map(payment, InvoicePayment.class));
        return modelMapper.map(entity, InvoicePaymentDto.class);
    }

    public InvoicePaymentDto getById(int id) {
        Optional<InvoicePayment> payment = this.invoicePaymentRepository.findById(id);
        if (payment.isEmpty()) {
            throw new EntityNotFoundException(id);
        }
        return modelMapper.map(payment.get(), InvoicePaymentDto.class);
    }

    public List<InvoicePaymentDto> getByInvoiceId(int invoiceId) {
        List<InvoicePayment> payments = this.invoicePaymentRepository.getByInvoiceId(invoiceId);
        return Arrays.asList(modelMapper.map(payments, InvoicePaymentDto[].class));
    }

    public List<InvoicePaymentDto> getMostRecentPayments(int invoiceId) {
        List<InvoicePayment> payments = this.invoicePaymentRepository.getMostRecent(invoiceId);
        return Arrays.asList(modelMapper.map(payments, InvoicePaymentDto[].class));
    }
    
}