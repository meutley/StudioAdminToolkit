package com.meutley.studioadmintoolkit.invoice;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final ModelMapper modelMapper;
    private final InvoiceNumberGeneratorService invoiceNumberGeneratorService;
    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(
        ModelMapper modelMapper,
        InvoiceNumberGeneratorService invoiceNumberGeneratorService,
        InvoiceRepository invoiceRepository
    ) {
        this.modelMapper = modelMapper;
        this.invoiceNumberGeneratorService = invoiceNumberGeneratorService;
        this.invoiceRepository = invoiceRepository;
    }
    
    @Override
    public InvoiceDto create(InvoiceDto invoice) {
        invoice.setInvoiceNumber(this.invoiceNumberGeneratorService.generateInvoiceNumber(invoice));
        Invoice entity = modelMapper.map(invoice, Invoice.class);
        entity.getLineItems().forEach(lineItem -> lineItem.setInvoice(entity));
        return modelMapper.map(this.invoiceRepository.save(entity), InvoiceDto.class);
    }
    
    @Override
    public List<InvoiceDto> getByClientId(int clientId) {
        List<Invoice> invoices = this.invoiceRepository.getByClientId(clientId);
        return Arrays.asList(modelMapper.map(invoices, InvoiceDto[].class));
    }

    @Override
    public InvoiceDto getById(int id) {
        return modelMapper.map(this.invoiceRepository.getOne(id), InvoiceDto.class);
    }

}