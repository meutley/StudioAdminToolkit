package com.meutley.studioadmintoolkit.invoice.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicePaymentRepository extends JpaRepository<InvoicePayment, Integer> {

    @Query(value = "SELECT * FROM invoice_payment WHERE invoice_id = ?1", nativeQuery = true)
    List<InvoicePayment> getByInvoiceId(int invoiceId);

    @Query(value = "SELECT * FROM invoice_payment WHERE invoice_id = ?1 ORDER BY date_collected, id", nativeQuery = true)
    List<InvoicePayment> getMostRecent(int invoiceId);
    
}