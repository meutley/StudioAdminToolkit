package com.meutley.studioadmintoolkit.studiosession;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "studio_session")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class StudioSession implements Serializable {

    private static final long serialVersionUID = -1654892502416448L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    public long getId() {
        return this.id;
    }
    
}