package com.meutley.studioadmintoolkit.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meutley.studioadmintoolkit.core.model.SoftDeleteEntity;
import com.meutley.studioadmintoolkit.studiosession.StudioSession;

@Entity
@Table(name = "client")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Client extends SoftDeleteEntity implements Serializable {

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

    @OneToMany(mappedBy = "client")
    private List<StudioSession> studioSessions = new ArrayList<>();

    public String getEmail() {
        return this.email;
    }
    
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<StudioSession> getStudioSessions() {
        return this.studioSessions;
    }

    public void setEmail(String value) {
        this.email = value;
    }
    
    public void setName(String value) {
        this.name = value;
    }

    public void addStudioSession(StudioSession studioSession) {
        this.studioSessions.add(studioSession);
        studioSession.setClient(this);
    }
    
}