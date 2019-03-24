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
public class ClientControllerTests {

    private static final int CLIENT_ID = 1;
    
    private MockMvc mvc;
    
    @Mock
    private Validator validator;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        target = new ClientController(
            clientService
        );
        
        mvc = MockMvcBuilders.standaloneSetup(target)
            .setControllerAdvice(new CoreResponseStatusExceptionHandler())
            .build();
    }

    @Test
    public void indexShouldCallClientServiceGetAll() throws Exception {
        when(clientService.getAll()).thenReturn(new ArrayList<>());

        mvc.perform(get("/client"));

        verify(clientService, times(1)).getAll();
    }

    @Test
    public void indexShouldReturnValidResponseWithClientList() throws Exception {
        List<ClientDto> clients = new ArrayList<>();
        clients.add(new ClientDto());
        when(clientService.getAll()).thenReturn(clients);

        mvc.perform(get("/client"))
            .andExpect(status().isOk())
            .andExpect(view().name("client/index"))
            .andExpect(model().attributeExists("clients"))
            .andExpect(model().attribute("clients", hasSize(1)));
    }

    @Test
    public void createShouldReturnValidResponseWithViewModel() throws Exception {
        mvc.perform(get("/client/create"))
            .andExpect(status().isOk())
            .andExpect(view().name("client/create"))
            .andExpect(model().attributeExists("viewModel"))
            .andExpect(model().attribute("viewModel",
                hasProperty("hasMailingAddress", is(false))));
    }

    @Test
    public void editWhenEntityNotFoundShouldThrowEntityNotFoundException() throws Exception {
        when(clientService.getById(anyInt())).thenThrow(EntityNotFoundException.class);

        mvc.perform(get("/client/" + CLIENT_ID + "/edit"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void editWhenClientFoundShouldReturnValidResponseWithViewModel() throws Exception {
        ClientDto client = new ClientDto();
        client.setId(CLIENT_ID);
        when(clientService.getById(anyInt())).thenReturn(client);

        mvc.perform(get("/client/" + CLIENT_ID + "/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("client/edit"))
            .andExpect(model().attributeExists("viewModel"))
            .andExpect(model().attribute("viewModel",
                hasProperty("client", is(client))));
    }

    @Test
    public void editShouldCallClientServiceGetById() throws Exception {
        when(clientService.getById(anyInt())).thenReturn(new ClientDto());

        mvc.perform(get("/client/" + CLIENT_ID + "/edit"));

        verify(clientService, times(1)).getById(CLIENT_ID);
    }
    
}