package com.meutley.studioadmintoolkit.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(value = "SELECT * FROM client WHERE email = ?1", nativeQuery = true)
    Client findByEmail(String email);
    
}