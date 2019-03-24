package com.meutley.studioadmintoolkit.invoice.payment;

import static org.junit.Assert.assertTrue;
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
public class InvoicePaymentServiceTests {

    private static final int INVOICE_ID = 1;
    private static final int INVOICE_PAYMENT_ID = 2;
    
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private InvoicePaymentRepository invoicePaymentRepository;

    @InjectMocks
    private InvoicePaymentServiceImpl target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        target = new InvoicePaymentServiceImpl(
            modelMapper,
            invoicePaymentRepository
        );

        when(modelMapper.map(any(InvoicePaymentDto.class), eq(InvoicePayment.class))).thenReturn(new InvoicePayment());
        when(modelMapper.map(any(InvoicePayment.class), eq(InvoicePaymentDto.class))).thenReturn(new InvoicePaymentDto());
        when(modelMapper.map(any(List.class), eq(InvoicePaymentDto[].class))).thenReturn(new InvoicePaymentDto[] {});
        when(invoicePaymentRepository.save(any(InvoicePayment.class))).thenReturn(new InvoicePayment());
    }

    @Test
    public void createShouldCallInvoicePaymentRepositorySave() {
        target.create(new InvoicePaymentDto());

        verify(invoicePaymentRepository, times(1)).save(any(InvoicePayment.class));
    }

    @Test
    public void createShouldMapInvoicePaymentDtoToEntity() {
        final InvoicePaymentDto dto = new InvoicePaymentDto();
        
        target.create(dto);

        verify(modelMapper, times(1)).map(dto, InvoicePayment.class);
    }

    @Test
    public void createShouldMapEntityToInvoicePaymentDto() {
        final InvoicePayment entity = new InvoicePayment();
        when(invoicePaymentRepository.save(any(InvoicePayment.class))).thenReturn(entity);

        target.create(new InvoicePaymentDto());

        verify(modelMapper, times(1)).map(entity, InvoicePaymentDto.class);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdWhenNotFoundShouldThrowEntityNotFoundException() {
        when(invoicePaymentRepository.findById(any(int.class))).thenReturn(Optional.empty());
        
        target.getById(INVOICE_PAYMENT_ID);
    }

    @Test
    public void getByIdWhenFoundShouldMapEntityToInvoicePaymentDto() {
        when(invoicePaymentRepository.findById(any(int.class))).thenReturn(Optional.of(new InvoicePayment()));

        target.getById(INVOICE_PAYMENT_ID);

        verify(modelMapper, times(1)).map(any(InvoicePayment.class), eq(InvoicePaymentDto.class));
    }

    @Test
    public void getByIdShouldCallRepositoryFindById() {
        when(invoicePaymentRepository.findById(any(int.class))).thenReturn(Optional.of(new InvoicePayment()));

        target.getById(INVOICE_PAYMENT_ID);

        verify(invoicePaymentRepository, times(1)).findById(INVOICE_PAYMENT_ID);
    }

    @Test
    public void getByInvoiceIdWhenNotFoundShouldReturnEmptyList() {
        when(invoicePaymentRepository.getByInvoiceId(any(int.class))).thenReturn(new ArrayList<>());

        final List<InvoicePaymentDto> actual = target.getByInvoiceId(INVOICE_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void getByInvoiceIdShouldMapEntitiesToInvoicePaymentDtoList() {
        when(invoicePaymentRepository.getByInvoiceId(any(int.class))).thenReturn(new ArrayList<>());

        target.getByInvoiceId(INVOICE_ID);

        verify(modelMapper, times(1)).map(any(List.class), eq(InvoicePaymentDto[].class));
    }

    @Test
    public void getByInvoiceIdShouldCallRepositoryGetByInvoiceId() {
        target.getByInvoiceId(INVOICE_ID);

        verify(invoicePaymentRepository, times(1)).getByInvoiceId(INVOICE_ID);
    }

    @Test
    public void getMostRecentPaymentsWhenNotFoundShouldReturnEmptyList() {
        when(invoicePaymentRepository.getMostRecent(any(int.class))).thenReturn(new ArrayList<>());

        final List<InvoicePaymentDto> actual = target.getMostRecentPayments(INVOICE_ID);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void getMostRecentPaymentsShouldMapEntitiesToInvoicePaymentDtoList() {
        when(invoicePaymentRepository.getByInvoiceId(any(int.class))).thenReturn(new ArrayList<>());

        target.getMostRecentPayments(INVOICE_ID);

        verify(modelMapper, times(1)).map(any(List.class), eq(InvoicePaymentDto[].class));
    }

    @Test
    public void getMostRecentPaymentsShouldCallRepositoryGetMostRecent() {
        target.getMostRecentPayments(INVOICE_ID);

        verify(invoicePaymentRepository, times(1)).getMostRecent(INVOICE_ID);
    }
    
}