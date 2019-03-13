package com.meutley.studioadmintoolkit.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class ClientServiceTests {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        target = new ClientServiceImpl(
            modelMapper,
            clientRepository
        );
    }

    @Test
    public void createShouldCallRepository() {
        target.create(new ClientDto());

        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    public void createShouldReturnNewClientObject() {
        when(clientRepository.save(any(Client.class))).thenReturn(new Client());

        ClientDto input = new ClientDto();
        input.setName("Test Client");
        input.setEmail("testclient@satk");

        ClientDto result = target.create(input);

        assertNotNull(result);
    }

    @Test
    public void editShouldCallRepositoryGetOne() {
        when(clientRepository.getOne(anyInt())).thenReturn(new Client());

        target.edit(1, new ClientDto());

        verify(clientRepository, times(1)).getOne(anyInt());
    }

    @Test
    public void editShouldCallRepositorySaveWithUpdatedEntity() {
        when(clientRepository.getOne(anyInt())).thenReturn(new Client());

        ClientDto input = new ClientDto();
        input.setName("Test Client");
        input.setEmail("testclient@satk");

        target.edit(0, input);

        verify(clientRepository, times(1)).save(
            argThat((Client c) ->
                c.getName().equals("Test Client")
                && c.getEmail().equals("testclient@satk")));
    }

    @Test
    public void editShouldReturnUpdatedEntity() {
        when(clientRepository.getOne(anyInt())).thenReturn(new Client());

        ClientDto input = new ClientDto();
        input.setName("Test Client");
        input.setEmail("testclient@satk");

        ClientDto actual = target.edit(0, input);

        assertEquals(input.getName(), actual.getName());
        assertEquals(input.getEmail(), actual.getEmail());
    }

    @Test
    public void getAllShouldCallRepository() {
        target.getAll();

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void getByEmailShouldCallRepository() {
        target.getByEmail("testclient@satk");

        verify(clientRepository, times(1)).findByEmail("testclient@satk");
    }

    @Test
    public void getByIdShouldCallRepository() {
        target.getById(1);

        verify(clientRepository, times(1)).getOne(1);
    }
    
}