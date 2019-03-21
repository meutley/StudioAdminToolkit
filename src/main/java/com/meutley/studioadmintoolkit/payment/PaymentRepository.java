package com.meutley.studioadmintoolkit.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(value = "SELECT * FROM payment WHERE client_id = ?1", nativeQuery = true)
    List<Payment> getByClientId(int clientId);

    @Query(value = "SELECT * FROM payment WHERE client_id = ?1 AND invoice_id = ?2", nativeQuery = true)
    List<Payment> getByClientInvoiceId(int clientId, int invoiceId);
    
}