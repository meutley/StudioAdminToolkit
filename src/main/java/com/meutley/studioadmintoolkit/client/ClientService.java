package com.meutley.studioadmintoolkit.client;

import java.util.List;

public interface ClientService {

    Client create(Client client);
    Client edit(int id, Client client);
    List<Client> getAll();
    Client getByEmail(String email);
    Client getById(int id);
    
}