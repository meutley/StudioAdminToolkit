package com.meutley.studioadmintoolkit.client;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Validator;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;
import com.meutley.studioadmintoolkit.core.web.CoreResponseStatusExceptionHandler;
import com.meutley.studioadmintoolkit.invoice.InvoiceDto;
import com.meutley.studioadmintoolkit.invoice.InvoiceService;
import com.meutley.studioadmintoolkit.invoice.payment.InvoicePaymentService;
import com.meutley.studioadmintoolkit.product.ProductDto;
import com.meutley.studioadmintoolkit.product.ProductService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientInvoiceControllerTests {

    private static final int CLIENT_ID = 1;
    private static final int INVOICE_ID = 2;
    private static final int INVOICE_LINE_ITEM_ID = 3;
    private static final String CLIENT_NAME = "CLIENT";
    
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

    @Test
    public void indexShouldCallInvoiceServiceGetByClientId() throws Exception {
        when(clientService.getById(anyInt())).thenReturn(new ClientDto());
        when(invoiceService.getByClientId(anyInt())).thenReturn(new ArrayList<>());

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice"));

        verify(invoiceService, times(1)).getByClientId(CLIENT_ID);
    }

    @Test
    public void indexShouldReturnValidResponseWithViewModel() throws Exception {
        final ClientDto client = new ClientDto();
        client.setId(CLIENT_ID);
        client.setName(CLIENT_NAME);
        
        final InvoiceDto invoice = new InvoiceDto();
        final List<InvoiceDto> invoices = new ArrayList<>();
        invoices.add(invoice);

        when(clientService.getById(anyInt())).thenReturn(client);
        when(invoiceService.getByClientId(anyInt())).thenReturn(invoices);
        
        mvc.perform(get("/client/" + CLIENT_ID + "/invoice"))
            .andExpect(status().isOk())
            .andExpect(view().name("client/invoice/index"))
            .andExpect(model().attributeExists("viewModel"))
            .andExpect(model().attribute("viewModel",
                allOf(
                    hasProperty("clientId", is(CLIENT_ID)),
                    hasProperty("clientName", is(CLIENT_NAME)),
                    hasProperty("invoices", is(invoices))
                )));
    }

    @Test
    public void createWhenClientDoesNotExistShouldReturnNotFoundResponse() throws Exception {
        when(clientService.getById(anyInt())).thenThrow(new EntityNotFoundException(anyInt()));

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/create"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void createShouldReturnValidResponseWithViewModel() throws Exception {
        final ClientDto client = new ClientDto();
        client.setId(CLIENT_ID);
        client.setName(CLIENT_NAME);

        when(clientService.getById(anyInt())).thenReturn(client);

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/create"))
            .andExpect(status().isOk())
            .andExpect(view().name("client/invoice/create"))
            .andExpect(model().attributeExists("viewModel"))
            .andExpect(model().attribute("viewModel",
                allOf(
                    hasProperty("client", is(client)),
                    hasProperty("invoice")
                )));
    }

    @Test
    public void editWhenClientDoesNotExistShouldReturnNotFoundResponse() throws Exception {
        when(clientService.getById(anyInt())).thenThrow(new EntityNotFoundException(anyInt()));

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/edit"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void editWhenInvoiceDoesNotExistShouldReturnNotFoundResponse() throws Exception {
        when(clientService.getById(anyInt())).thenReturn(new ClientDto());
        when(invoiceService.getById(anyInt())).thenThrow(new EntityNotFoundException(anyInt()));

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/edit"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void editShouldReturnValidResponseWithViewModel() throws Exception {
        final ClientDto client = new ClientDto();
        final InvoiceDto invoice = new InvoiceDto();

        when(clientService.getById(anyInt())).thenReturn(client);
        when(invoiceService.getById(anyInt())).thenReturn(invoice);

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/edit"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("viewModel"))
            .andExpect(model().attribute("viewModel",
                allOf(
                    hasProperty("client", is(client)),
                    hasProperty("invoice", is(invoice))
                )));
    }

    @Test
    public void deleteLineItemWhenLineItemNotFoundShouldReturnNotFoundResponse() throws Exception {
        doThrow(EntityNotFoundException.class).when(invoiceService).deleteLineItem(anyInt(), anyInt());

        mvc.perform(delete("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/delete-line-item/" + INVOICE_LINE_ITEM_ID))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteLineItemShouldReturnNoContentResponse() throws Exception {
        doNothing().when(invoiceService).deleteLineItem(anyInt(), anyInt());

        mvc.perform(delete("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/delete-line-item/" + INVOICE_LINE_ITEM_ID))
            .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void deleteLineItemShouldCallInvoiceServiceDeleteLineItem() throws Exception {
        doNothing().when(invoiceService).deleteLineItem(anyInt(), anyInt());

        mvc.perform(delete("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/delete-line-item/" + INVOICE_LINE_ITEM_ID));

        verify(invoiceService, times(1)).deleteLineItem(INVOICE_ID, INVOICE_LINE_ITEM_ID);
    }

    @Test
    public void selectProductModalShouldCallProductServiceGetAll() throws Exception {
        when(productService.getAll()).thenReturn(new ArrayList<>());

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/select-product-modal"));

        verify(productService, times(1)).getAll();
    }
    
    @Test
    public void selectProductModalWhenNoProductsShouldReturnModalViewWithEmptyModel() throws Exception {
        when(productService.getAll()).thenReturn(new ArrayList<>());

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/select-product-modal"))
            .andExpect(status().isOk())
            .andExpect(view().name("client/invoice/_select-product-modal"))
            .andExpect(model().attributeExists("products"))
            .andExpect(model().attribute("products", empty()));
    }

    @Test
    public void selectProductModalShouldReturnValidResponseWithProducts() throws Exception {
        List<ProductDto> products = buildProductsList(3);
        when(productService.getAll()).thenReturn(products);
        
        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/select-product-modal"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("products"))
            .andExpect(model().attribute("products",
                allOf(
                    hasSize(3),
                    containsInAnyOrder(
                        Arrays.asList(
                            equalTo(products.get(0)),
                            equalTo(products.get(1)),
                            equalTo(products.get(2)))))));
    }

    @Test
    public void viewWhenInvoiceNotFoundShouldReturnNotFoundResponse() throws Exception {
        when(invoiceService.getById(anyInt())).thenThrow(new EntityNotFoundException(anyInt()));

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/view"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void viewShouldCallInvoiceServiceGetById() throws Exception {
        when(invoiceService.getById(anyInt())).thenReturn(new InvoiceDto());

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/view"));

        verify(invoiceService, times(1)).getById(INVOICE_ID);
    }

    @Test
    public void viewShouldReturnValidResponseWithViewModel() throws Exception {
        ClientDto client = new ClientDto();
        InvoiceDto invoice = new InvoiceDto();
        invoice.setClient(client);
        
        when(invoicePaymentService.getMostRecentPayments(anyInt())).thenReturn(new ArrayList<>());
        when(invoiceService.getById(anyInt())).thenReturn(invoice);

        mvc.perform(get("/client/" + CLIENT_ID + "/invoice/" + INVOICE_ID + "/view"))
            .andExpect(status().isOk())
            .andExpect(view().name("client/invoice/view"))
            .andExpect(model().attributeExists("viewModel"))
            .andExpect(model().attribute("viewModel",
                allOf(
                    hasProperty("client", is(client)),
                    hasProperty("invoice", is(invoice)),
                    hasProperty("paymentHistory")
                )));
    }

    private static List<ProductDto> buildProductsList(int count) {
        List<ProductDto> products = new ArrayList<>();
        for (int x = count; x > 0; --x) {
            ProductDto product = new ProductDto();
            product.setName("Product" + x);
            products.add(product);
        }
        return products;
    }
    
}