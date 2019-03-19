package com.meutley.studioadmintoolkit.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceLineItemRepository extends JpaRepository<InvoiceLineItem, Integer> {

    @Query(value = "SELECT * FROM invoice_line_item WHERE id = ?1 AND invoice_id = ?2", nativeQuery = true)
    InvoiceLineItem getOneForInvoice(int invoiceId, int lineItemId);
    
}