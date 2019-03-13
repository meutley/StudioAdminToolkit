package com.meutley.studioadmintoolkit.client;

import java.util.Arrays;
import java.util.List;

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
        Client clientEntity = this.clientRepository.save(modelMapper.map(client, Client.class));
        return modelMapper.map(clientEntity, ClientDto.class);
    }

    @Override
    public ClientDto edit(int id, ClientDto client) {
        Client existingClient = this.clientRepository.getOne(id);
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        this.clientRepository.save(existingClient);

        return modelMapper.map(existingClient, ClientDto.class);
    }

    @Override
    public List<ClientDto> getAll() {
        return Arrays.asList(modelMapper.map(this.clientRepository.findAll(), ClientDto[].class));
    }
    
    @Override
    public ClientDto getByEmail(String email) {
        return modelMapper.map(this.clientRepository.findByEmail(email), ClientDto.class);
    }
    
    @Override
    public ClientDto getById(int id) {
        return modelMapper.map(this.clientRepository.getOne(id), ClientDto.class);
    }

}