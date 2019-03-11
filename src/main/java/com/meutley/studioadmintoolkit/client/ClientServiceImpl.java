package com.meutley.studioadmintoolkit.client;

import java.util.List;

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
    public List<Client> getAll() {
        return this.clientRepository.findAll();
    }
    
    @Override
    public Client getByEmail(String email) {
        return this.clientRepository.findByEmail(email);
    }
    
    @Override
    public Client getById(int id) {
        return this.clientRepository.getOne(id);
    }

}