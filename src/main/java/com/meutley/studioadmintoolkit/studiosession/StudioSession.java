package com.meutley.studioadmintoolkit.studiosession;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meutley.studioadmintoolkit.client.Client;
import com.meutley.studioadmintoolkit.core.model.SoftDeleteEntity;

@Entity
@Table(name = "studio_session")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class StudioSession extends SoftDeleteEntity implements Serializable {

    private static final long serialVersionUID = -1654892502416448L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters in length")
    private String name;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Client getClient() {
        return this.client;
    }
    
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setName(String value) {
        this.name = value;
    }
    
}