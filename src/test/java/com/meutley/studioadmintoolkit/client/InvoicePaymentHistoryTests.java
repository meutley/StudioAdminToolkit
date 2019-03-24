package com.meutley.studioadmintoolkit.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.meutley.studioadmintoolkit.invoice.payment.InvoicePaymentDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoicePaymentHistoryTests {

    private InvoicePaymentHistory target;

    @Before
    public void setUp() {
        target = new InvoicePaymentHistory();
    }

    @Test
    public void buildPaymentHistoryWhenNoPaymentsShouldSetRemainingBalanceToInvoiceTotal() {
        final BigDecimal invoiceTotal = new BigDecimal(103.75);

        target.buildPaymentHistory(invoiceTotal, new ArrayList<>());

        assertEquals(invoiceTotal, target.getRemainingBalance());
    }

    @Test
    public void buildPaymentHistoryWhenNoPaymentsShouldSetPaymentHistoryRowsToEmptyList() {
        target.buildPaymentHistory(BigDecimal.ZERO, new ArrayList<>());

        assertTrue(target.getPaymentHistoryRows().isEmpty());
    }

    @Test
    public void buildPaymentHistoryWhenPaymentsPresentShouldSubtractTotalPaymentsFromInvoiceTotal() {
        final List<InvoicePaymentDto> payments = new ArrayList<>();
        final InvoicePaymentDto payment = new InvoicePaymentDto();
        payment.setDateCollected(new Date());
        payment.setAmount(new BigDecimal(10));
        payments.add(payment);

        final BigDecimal invoiceTotal = new BigDecimal(20);
        final BigDecimal expected = invoiceTotal.subtract(payment.getAmount());

        target.buildPaymentHistory(invoiceTotal, payments);

        assertEquals(expected, target.getRemainingBalance());
    }
    
}