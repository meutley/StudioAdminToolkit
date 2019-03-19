package com.meutley.studioadmintoolkit.invoice;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceServiceTests {

    private static final int CLIENT_ID = 1;
    private static final int INVOICE_ID = 2;
    private static final int LINE_ITEM_ID = 3;
    
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private InvoiceNumberGeneratorService invoiceNumberGeneratorService;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private InvoiceLineItemRepository invoiceLineItemRepository;
    
    @InjectMocks
    private InvoiceServiceImpl target;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        target = new InvoiceServiceImpl(
            modelMapper,
            invoiceNumberGeneratorService,
            invoiceRepository,
            invoiceLineItemRepository
        );

        when(modelMapper.map(any(InvoiceDto.class), any())).thenReturn(new Invoice());
        when(modelMapper.map(any(Invoice.class), any())).thenReturn(new InvoiceDto());
        when(modelMapper.map(any(List.class), eq(InvoiceDto[].class))).thenReturn(new InvoiceDto[]{});
        when(invoiceNumberGeneratorService.generateInvoiceNumber(any(InvoiceDto.class))).thenReturn(new String());
        when(invoiceRepository.findById(any(int.class))).thenReturn(Optional.of(new Invoice()));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(new Invoice());
    }

    @Test
    public void createShouldInvokeInvoiceNumberGeneratorService() {
        target.create(new InvoiceDto());

        verify(invoiceNumberGeneratorService, times(1)).generateInvoiceNumber(any(InvoiceDto.class));
    }

    @Test
    public void createShouldMapDtoToEntity() {
        final InvoiceDto input = new InvoiceDto();
        target.create(input);

        verify(modelMapper, times(1)).map(input, Invoice.class);
    }

    @Test
    public void createShouldCallSetInvoiceForEachLineItem() {
        final List<InvoiceLineItem> lineItems = new ArrayList<>();
        lineItems.add(new InvoiceLineItem());
        lineItems.add(new InvoiceLineItem());
        final Invoice entity = new Invoice();
        entity.setLineItems(lineItems);

        when(modelMapper.map(any(InvoiceDto.class), any())).thenReturn(entity);

        target.create(new InvoiceDto());

        assertEquals(entity.getLineItems().get(0).getInvoice(), entity);
        assertEquals(entity.getLineItems().get(1).getInvoice(), entity);
    }

    @Test
    public void createShouldMapEntityToDto() {
        final Invoice entity = new Invoice();
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(entity);

        target.create(new InvoiceDto());

        verify(modelMapper, times(1)).map(entity, InvoiceDto.class);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteLineItemWhenIdNotFoundShouldThrowEntityNotFoundException() {
        when(invoiceLineItemRepository.getOneForInvoice(any(int.class), any(int.class)))
            .thenReturn(null);

        target.deleteLineItem(INVOICE_ID, LINE_ITEM_ID);
    }

    @Test
    public void deleteLineItemWhenFoundShouldCallInvoiceLineItemRepositoryDeleteById() {
        when(invoiceLineItemRepository.getOneForInvoice(any(int.class), any(int.class)))
            .thenReturn(new InvoiceLineItem());

        target.deleteLineItem(CLIENT_ID, LINE_ITEM_ID);

        verify(invoiceLineItemRepository, times(1)).deleteById(LINE_ITEM_ID);
    }

    @Test
    public void getByClientIdShouldCallInvoiceRepositoryGetByClientId() {
        final List<Invoice> results = new ArrayList<>();
        when(invoiceRepository.getByClientId(CLIENT_ID)).thenReturn(results);
        target.getByClientId(CLIENT_ID);

        verify(invoiceRepository, times(1)).getByClientId(CLIENT_ID);
    }

    @Test
    public void getByClientIdShouldMapEntityListToDtoArray() {
        final List<Invoice> results = new ArrayList<>();
        when(invoiceRepository.getByClientId(CLIENT_ID)).thenReturn(results);

        target.getByClientId(CLIENT_ID);

        verify(modelMapper, times(1)).map(results, InvoiceDto[].class);
    }

    @Test
    public void getByIdShouldCallInvoiceRepositoryFindById() {
        target.getById(INVOICE_ID);

        verify(invoiceRepository, times(1)).findById(INVOICE_ID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdWhenIdNotFoundShouldThrowEntityNotFoundException() {
        when(invoiceRepository.findById(any(int.class))).thenReturn(Optional.empty());

        target.getById(INVOICE_ID);
    }

    @Test
    public void getByIdShouldMapEntityToDto() {
        when(invoiceRepository.findById(any(int.class))).thenReturn(Optional.of(new Invoice()));

        target.getById(INVOICE_ID);

        verify(modelMapper, times(1)).map(any(Invoice.class), eq(InvoiceDto.class));
    }
    
}