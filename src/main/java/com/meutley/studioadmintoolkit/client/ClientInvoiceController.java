package com.meutley.studioadmintoolkit.client;

import javax.validation.Valid;

import com.meutley.studioadmintoolkit.invoice.InvoiceDto;
import com.meutley.studioadmintoolkit.invoice.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("client/{clientId}/invoice")
public class ClientInvoiceController {

    @Autowired
    Validator validator;
    
    private final ClientService clientService;
    private final InvoiceService invoiceService;

    public ClientInvoiceController(
        ClientService clientService,
        InvoiceService invoiceService
    ) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
    }

    @GetMapping(value = {"", "/index"})
    public String index(@PathVariable int clientId, Model model) {
        ClientDto client = this.clientService.getById(clientId);
        ClientInvoiceListViewModel viewModel = new ClientInvoiceListViewModel();
        viewModel.setClientId(clientId);
        viewModel.setClientName(client.getName());
        viewModel.setInvoices(this.invoiceService.getByClientId(clientId));
        model.addAttribute("viewModel", viewModel);
        return "client/invoice/index";
    }

    @GetMapping("/create")
    public String create(@PathVariable int clientId, Model model) {
        if (!model.containsAttribute("viewModel")) {
            EditClientInvoiceViewModel viewModel = new EditClientInvoiceViewModel();
            viewModel.setClient(this.clientService.getById(clientId));
            viewModel.setInvoice(new InvoiceDto());
            model.addAttribute("viewModel", viewModel);
        }
        return "client/invoice/create";
    }

    @PostMapping("/create")
    public String create(@PathVariable int clientId, @Valid @ModelAttribute EditClientInvoiceViewModel viewModel,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        for (int i = 0; i < viewModel.getInvoice().getLineItems().size(); i++) {
            Object lineItem = viewModel.getInvoice().getLineItems().get(i);
            bindingResult.pushNestedPath(String.format("invoice.lineItems[%d]", i));
            validator.validate(lineItem, bindingResult);
            bindingResult.popNestedPath();
        }

        viewModel.setClient(this.clientService.getById(clientId));
        redirectAttributes.addAttribute("clientId", clientId);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel", bindingResult);
            redirectAttributes.addFlashAttribute("viewModel", viewModel);
            return "redirect:/client/{clientId}/invoice/create";
        }

        InvoiceDto invoice = viewModel.getInvoice();
        invoice.setClient(viewModel.getClient());
        this.invoiceService.create(invoice);
        return "redirect:/client/{clientId}/invoice";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int clientId, @PathVariable int id, Model model) {
        if (!model.containsAttribute("viewModel")) {
            EditClientInvoiceViewModel viewModel = new EditClientInvoiceViewModel();
            viewModel.setClient(this.clientService.getById(clientId));
            viewModel.setInvoice(this.invoiceService.getById(id));
            model.addAttribute("viewModel", viewModel);
        }
        return "client/invoice/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int clientId, @Valid @ModelAttribute EditClientInvoiceViewModel viewModel,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

            for (int i = 0; i < viewModel.getInvoice().getLineItems().size(); i++) {
                Object lineItem = viewModel.getInvoice().getLineItems().get(i);
                bindingResult.pushNestedPath(String.format("invoice.lineItems[%d]", i));
                validator.validate(lineItem, bindingResult);
                bindingResult.popNestedPath();
            }
    
            viewModel.setClient(this.clientService.getById(clientId));
            redirectAttributes.addAttribute("clientId", clientId);
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel", bindingResult);
                redirectAttributes.addFlashAttribute("viewModel", viewModel);
                redirectAttributes.addAttribute("id", viewModel.getInvoice().getId());
                return "redirect:/client/{clientId}/invoice/{id}/edit";
            }
    
            InvoiceDto invoice = viewModel.getInvoice();
            invoice.setClient(viewModel.getClient());
            this.invoiceService.create(invoice);
            return "redirect:/client/{clientId}/invoice";
    }

}