package com.meutley.studioadmintoolkit.client;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "client")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Client implements Serializable {

    private static final long serialVersionUID = -1651846186451658L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters in length")
    private String name;

    @Column(name = "email", unique = true)
    @Email(message = "Email must be a valid e-mail address")
    private String email;

    public String getEmail() {
        return this.email;
    }
    
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String value) {
        this.email = value;
    }
    
    public void setName(String value) {
        this.name = value;
    }
    
}