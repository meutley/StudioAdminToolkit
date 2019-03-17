package com.meutley.studioadmintoolkit.invoice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value = "SELECT * FROM invoice WHERE client_id = ?1", nativeQuery = true)
    List<Invoice> getByClientId(int clientId);
    
}