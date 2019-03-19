package com.meutley.studioadmintoolkit.client;

import java.util.Arrays;
import java.util.List;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;
import com.meutley.studioadmintoolkit.mailingaddress.MailingAddress;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;

    public ClientServiceImpl(
        ModelMapper modelMapper,
        ClientRepository clientRepository
    ) {
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
    }
    
    @Override
    public ClientDto create(ClientDto client) {
        Client clientEntity = modelMapper.map(client, Client.class);
        MailingAddress mailingAddress = client.getMailingAddress() != null
            ? modelMapper.map(client.getMailingAddress(), MailingAddress.class)
            : null;
        clientEntity.setMailingAddress(mailingAddress);
        return modelMapper.map(this.clientRepository.save(clientEntity), ClientDto.class);
    }

    @Override
    public ClientDto edit(int id, ClientDto client) {
        Client existingClient = this.clientRepository.getOne(id);
        if (existingClient == null) {
            throw new EntityNotFoundException(id);
        }
        
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        MailingAddress mailingAddress = client.getMailingAddress() != null
            ? modelMapper.map(client.getMailingAddress(), MailingAddress.class)
            : null;
        existingClient.setMailingAddress(mailingAddress);

        return modelMapper.map(this.clientRepository.save(existingClient), ClientDto.class);
    }

    @Override
    public List<ClientDto> getAll() {
        return Arrays.asList(modelMapper.map(this.clientRepository.findAll(), ClientDto[].class));
    }
    
    @Override
    public ClientDto getByEmail(String email) {
        Client existingClient = this.clientRepository.findByEmail(email);
        return existingClient != null
            ? modelMapper.map(existingClient, ClientDto.class)
            : null;
    }
    
    @Override
    public ClientDto getById(int id) {
        Client client = this.clientRepository.getOne(id);
        if (client == null) {
            throw new EntityNotFoundException(id);
        }
        return modelMapper.map(client, ClientDto.class);
    }

}