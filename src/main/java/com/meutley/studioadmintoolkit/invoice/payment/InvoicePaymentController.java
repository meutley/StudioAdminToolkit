package com.meutley.studioadmintoolkit.invoice.payment;

import java.util.Optional;

import javax.validation.Valid;

import com.meutley.studioadmintoolkit.invoice.InvoiceDto;
import com.meutley.studioadmintoolkit.invoice.InvoiceService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("invoice/{invoiceId}/payment")
public class InvoicePaymentController {

    private final InvoiceService invoiceService;
    private final InvoicePaymentService paymentService;

    public InvoicePaymentController(
        InvoiceService invoiceService,
        InvoicePaymentService paymentService
    ) {
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
    }

    @GetMapping(value = {"/edit-payment-modal", "/{id}/edit-payment-modal"})
    public String editPaymentModal(@PathVariable int invoiceId, @PathVariable Optional<Integer> id, Model model) {

        if (!model.containsAttribute("viewModel")) {
            InvoiceDto invoice = this.invoiceService.getById(invoiceId);
            EditInvoicePaymentModalViewModel viewModel = new EditInvoicePaymentModalViewModel();
            viewModel.setClientName(invoice.getClient().getName());
            viewModel.setInvoiceNumber(invoice.getInvoiceNumber());
            viewModel.setInvoiceId(invoiceId);
            if (id.isEmpty()) {
                viewModel.setIsNew(true);
            } else {
                InvoicePaymentDto payment = this.paymentService.getById(id.get());
                viewModel.setPayment(payment);
            }

            model.addAttribute("viewModel", viewModel);
        }
        return "client/invoice/payment/_edit-payment-modal";
    }

    @PostMapping("")
    public Object save(@PathVariable int invoiceId, @Valid @ModelAttribute EditInvoicePaymentModalViewModel viewModel,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        InvoiceDto invoice = this.invoiceService.getById(invoiceId);
        viewModel.setClientName(invoice.getClient().getName());
        viewModel.setInvoiceNumber(invoice.getInvoiceNumber());
        viewModel.setInvoiceId(invoiceId);
        viewModel.setIsNew(viewModel.getPayment().getId() == 0);

        redirectAttributes.addAttribute("invoiceId", invoiceId);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel", bindingResult);
            redirectAttributes.addFlashAttribute("viewModel", viewModel);
            return "redirect:/invoice/{invoiceId}/payment/edit-payment-modal";
        }

        viewModel.getPayment().setInvoice(invoice);
        this.paymentService.create(viewModel.getPayment());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    

}