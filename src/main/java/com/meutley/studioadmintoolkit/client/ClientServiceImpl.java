package com.meutley.studioadmintoolkit.client;

import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(
        ClientRepository clientRepository
    ) {
        this.clientRepository = clientRepository;
    }
    
    @Override
    public Client create(Client client) {
        return this.clientRepository.save(client);
    }

    @Override
    public Client edit(int id, Client client) {
        Client existingClient = this.clientRepository.getOne(id);
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        this.clientRepository.save(existingClient);

        return existingClient;
    }
    
    @Override
    public Client getById(int id) {
        return this.clientRepository.getOne(id);
    }

}