package com.meutley.studioadmintoolkit.client;

import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import com.meutley.studioadmintoolkit.core.EntityNotFoundException;
import com.meutley.studioadmintoolkit.invoice.InvoiceDto;
import com.meutley.studioadmintoolkit.invoice.InvoiceService;
import com.meutley.studioadmintoolkit.product.ProductDto;
import com.meutley.studioadmintoolkit.product.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final ProductService productService;

    public ClientInvoiceController(ClientService clientService, InvoiceService invoiceService,
            ProductService productService) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @GetMapping(value = { "", "/index" })
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
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel",
                    bindingResult);
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
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel",
                    bindingResult);
            redirectAttributes.addFlashAttribute("viewModel", viewModel);
            redirectAttributes.addAttribute("id", viewModel.getInvoice().getId());
            return "redirect:/client/{clientId}/invoice/{id}/edit";
        }

        InvoiceDto invoice = viewModel.getInvoice();
        invoice.setClient(viewModel.getClient());
        this.invoiceService.create(invoice);
        return "redirect:/client/{clientId}/invoice";
    }

    @DeleteMapping("/{id}/delete-line-item/{lineItemId}")
    public ResponseEntity<HttpStatus> deleteLineItem(@PathVariable int clientId, @PathVariable int id,
            @PathVariable int lineItemId) {
        this.invoiceService.deleteLineItem(id, lineItemId);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/select-product-modal")
    public String selectProductModal(Model model) {
        List<ProductDto> products = this.productService.getAll();
        products.sort(Comparator.comparing(ProductDto::getName));
        model.addAttribute("products", products);
        return "client/invoice/_select-product-modal";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int clientId, @PathVariable int id, Model model) {
        InvoiceDto invoice = this.invoiceService.getById(id);
        if (invoice.getClient().getId() != clientId) {
            throw new EntityNotFoundException(id);
        }

        ViewClientInvoiceViewModel viewModel = new ViewClientInvoiceViewModel();
        viewModel.setClient(invoice.getClient());
        viewModel.setInvoice(invoice);
        model.addAttribute("viewModel", viewModel);
        return "client/invoice/view";
    }
    

}