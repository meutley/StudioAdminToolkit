package com.meutley.studioadmintoolkit.client;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;
import com.meutley.studioadmintoolkit.core.web.CoreResponseStatusExceptionHandler;
import com.meutley.studioadmintoolkit.invoice.InvoiceService;
import com.meutley.studioadmintoolkit.invoice.payment.InvoicePaymentService;
import com.meutley.studioadmintoolkit.product.ProductService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientInvoiceControllerTests {

    private static final int CLIENT_ID = 1;
    
    private MockMvc mvc;
    
    @Mock
    private Validator validator;

    @Mock
    private ClientService clientService;

    @Mock
    private InvoiceService invoiceService;
    
    @Mock
    private InvoicePaymentService invoicePaymentService;
    
    @Mock
    private ProductService productService;

    @InjectMocks
    private ClientInvoiceController target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        target = new ClientInvoiceController(
            clientService,
            invoiceService,
            invoicePaymentService,
            productService
        );
        
        mvc = MockMvcBuilders.standaloneSetup(target)
            .setControllerAdvice(new CoreResponseStatusExceptionHandler())
            .build();
    }

    @Test
    public void indexShouldCallClientServiceGetById() throws Exception {
        when(clientService.getById(CLIENT_ID)).thenReturn(new ClientDto());

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice"));

        verify(clientService, times(1)).getById(CLIENT_ID);
    }
    
}