package com.meutley.studioadmintoolkit.studiosession;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.meutley.studioadmintoolkit.client.ClientDto;

public class StudioSessionDto implements Serializable {

    private static final long serialVersionUID = 7041765943238982365L;

    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters in length")
    private String name;

    private ClientDto client;

    public ClientDto getClient() {
        return this.client;
    }
    
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String value) {
        this.name = value;
    }
    
}