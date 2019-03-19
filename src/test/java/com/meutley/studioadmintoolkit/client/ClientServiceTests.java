package com.meutley.studioadmintoolkit.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class ClientServiceTests {

    private static final int CLIENT_ID = 1;
    
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

        when(modelMapper.map(any(ClientDto.class), eq(Client.class))).thenReturn(new Client());
        when(modelMapper.map(any(Client.class), eq(ClientDto.class))).thenReturn(new ClientDto());
        when(modelMapper.map(any(List.class), eq(ClientDto[].class))).thenReturn(new ClientDto[]{});
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
    public void editShouldCallRepositoryFindById() {
        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(new Client()));

        target.edit(1, new ClientDto());

        verify(clientRepository, times(1)).findById(anyInt());
    }

    @Test
    public void editShouldCallRepositorySaveWithUpdatedEntity() {
        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(new Client()));

        ClientDto input = new ClientDto();
        input.setName("Test Client");
        input.setEmail("testclient@satk");

        target.edit(CLIENT_ID, input);

        verify(clientRepository, times(1)).save(
            argThat((Client c) ->
                c.getName().equals("Test Client")
                && c.getEmail().equals("testclient@satk")));
    }

    @Test(expected = EntityNotFoundException.class)
    public void editWhenIdNotFoundShouldThrowEntityNotFoundException() {
        when(clientRepository.findById(anyInt())).thenReturn(Optional.empty());

        target.edit(CLIENT_ID, new ClientDto());
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
    public void getByEmailWhenFoundShouldMapEntityToDto() {
        when(clientRepository.findByEmail(any(String.class))).thenReturn(new Client());
        
        ClientDto actual = target.getByEmail("testclient@satk");

        verify(modelMapper, times(1)).map(any(Client.class), eq(ClientDto.class));
        assertNotNull(actual);
    }

    @Test
    public void getByEmailWhenNotFoundShouldReturNull() {
        when(clientRepository.findByEmail(any(String.class))).thenReturn(null);
        
        ClientDto actual = target.getByEmail("testclient@satk");

        verify(modelMapper, times(0)).map(any(Client.class), eq(ClientDto.class));
        assertNull(actual);
    }

    @Test
    public void getByIdShouldCallRepository() {
        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(new Client()));
        
        target.getById(CLIENT_ID);

        verify(clientRepository, times(1)).findById(CLIENT_ID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdWhenNotFoundShouldThrowEntityNotFoundException() {
        when(clientRepository.findById(any(int.class))).thenReturn(Optional.empty());

        target.getById(CLIENT_ID);
    }
    
}