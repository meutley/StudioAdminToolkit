package com.meutley.studioadmintoolkit.client;

public interface ClientService {

    Client create(Client client);
    Client edit(int id, Client client);
    Client getById(int id);
    
}