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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(value = {"/edit-payment-modal", "/{id}/edit-payment-modal"})
    public String editPaymentModal(@PathVariable Optional<Integer> id,
        @RequestParam Optional<Integer> clientId,
        @RequestParam Optional<Integer> invoiceId,
        Model model) {

        EditPaymentModalViewModel viewModel = new EditPaymentModalViewModel();
        viewModel.setClientId(clientId);
        viewModel.setInvoiceId(invoiceId);
        if (id.isPresent()) {
            PaymentDto payment = this.paymentService.getById(id.get());
            viewModel.setPayment(payment);
            viewModel.setClientName(payment.getClient().getName());
            if (payment.getInvoice() != null) {
                viewModel.setInvoiceNumber(payment.getInvoice().getInvoiceNumber());
            }
            viewModel.setIsNew(false);
        } else {
            if (clientId.isPresent()) {
                ClientDto client = this.clientService.getById(clientId.get());
                viewModel.setClientName(client.getName());
            }

            if (invoiceId.isPresent()) {
                InvoiceDto invoice = this.invoiceService.getById(invoiceId.get());
                viewModel.setInvoiceNumber(invoice.getInvoiceNumber());
            }
            
            viewModel.setIsNew(true);
        }

        model.addAttribute("viewModel", viewModel);
        return "payment/_edit-payment-modal";
    }

}