package com.meutley.studioadmintoolkit.invoice;

import com.meutley.studioadmintoolkit.client.ClientDto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceNumberGeneratorServiceTests {

    private InvoiceNumberGeneratorServiceImpl target;
    
    @Before
    public void setUp() {
        target = new InvoiceNumberGeneratorServiceImpl();
    }

    @Test
    public void generateInvoiceNumberShouldReturnWithPrefixControlCodeAndPaddedClientId() {
        final String expected = "SATK-IV-000015";

        InvoiceDto input = new InvoiceDto();
        ClientDto client = new ClientDto();
        client.setId(15);
        input.setClient(client);
        
        final String actual = target.generateInvoiceNumber(input);

        Assert.assertEquals(expected, actual);
    }
    
}