package com.meutley.studioadmintoolkit.client;

import java.util.List;

public interface ClientService {

    ClientDto create(ClientDto client);
    ClientDto edit(int id, ClientDto client);
    List<ClientDto> getAll();
    ClientDto getByEmail(String email);
    ClientDto getById(int id);
    
}