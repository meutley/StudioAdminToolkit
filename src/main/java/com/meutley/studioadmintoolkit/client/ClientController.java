package com.meutley.studioadmintoolkit.client;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("client")
public class ClientController {

    @Autowired
    private Validator validator;
    
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = { "", "/index" })
    public String index(Model model) {
        List<ClientDto> clients = this.clientService.getAll();
        model.addAttribute("clients", clients);
        return "client/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute("viewModel")) {
            EditClientViewModel viewModel = new EditClientViewModel();
            viewModel.setClient(new ClientDto());
            viewModel.setHasMailingAddress(false);
            model.addAttribute("viewModel", viewModel);
        }
        return "client/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute EditClientViewModel viewModel, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        
        ClientDto client = viewModel.getClient();
        if (viewModel.getHasMailingAddress() == true) {
            bindingResult.pushNestedPath("client.mailingAddress");
            validator.validate(client.getMailingAddress(), bindingResult);
            bindingResult.popNestedPath();
        } else {
            client.setMailingAddress(null);
        }
        
        ClientDto existingByEmail = this.clientService.getByEmail(client.getEmail());
        if (existingByEmail != null) {
            bindingResult.addError(new FieldError("viewmodel", "client.email", client.getEmail(), false, null, null, "The e-mail address is already in use"));
        }
                
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel", bindingResult);
            redirectAttributes.addFlashAttribute("viewModel", viewModel);
            return "redirect:/client/create";
        }
        
        ClientDto newClient = this.clientService.create(client);
        redirectAttributes.addAttribute("id", newClient.getId());
        return "redirect:/client/{id}/details";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        if (!model.containsAttribute("viewModel")) {
            EditClientViewModel viewModel = new EditClientViewModel();
            viewModel.setClient(this.clientService.getById(id));
            viewModel.setHasMailingAddress(viewModel.doesClientHaveMailingAddress());
            model.addAttribute("viewModel", viewModel);
        }
        return "client/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute EditClientViewModel viewModel, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        
        ClientDto client = viewModel.getClient();
        if (viewModel.getHasMailingAddress() == true) {
            bindingResult.pushNestedPath("client.mailingAddress");
            validator.validate(client.getMailingAddress(), bindingResult);
            bindingResult.popNestedPath();
        } else {
            client.setMailingAddress(null);
        }

        ClientDto existingByEmail = this.clientService.getByEmail(client.getEmail());
        if (existingByEmail != null && existingByEmail.getId() != id) {
            bindingResult.addError(new FieldError("viewModel", "client.email", client.getEmail(), false, null, null, "The e-mail address is already in use"));
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel", bindingResult);
            redirectAttributes.addFlashAttribute("viewModel", viewModel);
            if (!redirectAttributes.containsAttribute("id")) {
                redirectAttributes.addAttribute("id", id);
            }
            return "redirect:/client/{id}/edit";
        }
        
        ClientDto newClient = this.clientService.edit(id, client);
        redirectAttributes.addAttribute("id", newClient.getId());
        return "redirect:/client/{id}/details";
    }
    
}