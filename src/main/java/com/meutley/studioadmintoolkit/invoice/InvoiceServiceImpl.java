package com.meutley.studioadmintoolkit.invoice;

import java.util.Arrays;
import java.util.List;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final ModelMapper modelMapper;
    private final InvoiceNumberGeneratorService invoiceNumberGeneratorService;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceLineItemRepository invoiceLineItemRepository;

    public InvoiceServiceImpl(
        ModelMapper modelMapper,
        InvoiceNumberGeneratorService invoiceNumberGeneratorService,
        InvoiceRepository invoiceRepository,
        InvoiceLineItemRepository invoiceLineItemRepository
    ) {
        this.modelMapper = modelMapper;
        this.invoiceNumberGeneratorService = invoiceNumberGeneratorService;
        this.invoiceRepository = invoiceRepository;
        this.invoiceLineItemRepository = invoiceLineItemRepository;
    }
    
    @Override
    public InvoiceDto create(InvoiceDto invoice) {
        invoice.setInvoiceNumber(this.invoiceNumberGeneratorService.generateInvoiceNumber(invoice));
        Invoice entity = modelMapper.map(invoice, Invoice.class);
        entity.getLineItems().forEach(lineItem -> lineItem.setInvoice(entity));
        return modelMapper.map(this.invoiceRepository.save(entity), InvoiceDto.class);
    }

    @Override
    public void deleteLineItem(int invoiceId, int lineItemId) {
        this.invoiceLineItemRepository.deleteById(lineItemId);
    }
    
    @Override
    public List<InvoiceDto> getByClientId(int clientId) {
        List<Invoice> invoices = this.invoiceRepository.getByClientId(clientId);
        return Arrays.asList(modelMapper.map(invoices, InvoiceDto[].class));
    }

    @Override
    public InvoiceDto getById(int id) {
        Invoice invoice = this.invoiceRepository.getOne(id);
        if (invoice == null) {
            throw new EntityNotFoundException(id);
        }
        return modelMapper.map(invoice, InvoiceDto.class);
    }

}