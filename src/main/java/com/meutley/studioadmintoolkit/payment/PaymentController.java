package com.meutley.studioadmintoolkit.payment;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.client.ClientDto;
import com.meutley.studioadmintoolkit.client.ClientService;
import com.meutley.studioadmintoolkit.invoice.InvoiceDto;
import com.meutley.studioadmintoolkit.invoice.InvoiceService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("payment")
public class PaymentController {

    private final ClientService clientService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;

    public PaymentController(
        ClientService clientService,
        InvoiceService invoiceService,
        PaymentService paymentService
    ) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
    }

    @GetMapping(value = {"", "/", "/client/{clientId}", "/client/{clientId}/invoice/{invoiceId}"})
    public String index(@PathVariable Optional<Integer> clientId, @PathVariable Optional<Integer> invoiceId, Model model) {
        PaymentListViewModel viewModel = new PaymentListViewModel();
        List<ClientDto> clients = this.clientService.getAll();
        clients.sort(Comparator.comparing(ClientDto::getName));
        viewModel.setClients(clients);
        viewModel.setSelectedClientId(clientId);
        viewModel.setSelectedInvoiceId(invoiceId);
        
        if (clientId.isPresent()) {
            List<InvoiceDto> invoices = this.invoiceService.getByClientId(clientId.get());
            viewModel.setInvoices(invoices);
            
            List<PaymentDto> payments =
                invoiceId.isEmpty()
                    ? this.paymentService.getByClientId(clientId.get())
                    : this.paymentService.getByClientInvoiceId(clientId.get(), invoiceId.get());

            viewModel.setPayments(payments);
        }
        model.addAttribute("viewModel", viewModel);
        return "payment/index";
    }

}