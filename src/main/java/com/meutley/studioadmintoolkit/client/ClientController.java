package com.meutley.studioadmintoolkit.client;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = { "", "/index" })
    public String index(Model model) {
        List<Client> clients = this.clientService.getAll();
        model.addAttribute("clients", clients);
        return "client/index";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable int id, Model model) {
        Client client = this.clientService.getById(id);
        model.addAttribute("client", client);
        return "client/details";
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute("client")) {
            model.addAttribute("client", new Client());
        }
        return "client/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Client client, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        
        Client existingByEmail = this.clientService.getByEmail(client.getEmail());
        if (existingByEmail != null) {
            bindingResult.addError(new FieldError("client", "email", client.getEmail(), false, null, null, "The e-mail address is already in use"));
        }
                
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.client", bindingResult);
            redirectAttributes.addFlashAttribute("client", client);
            return "redirect:/client/create";
        }
        
        Client newClient = this.clientService.create(client);
        redirectAttributes.addAttribute("id", newClient.getId());
        return "redirect:/client/{id}/details";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        if (!model.containsAttribute("client")) {
            model.addAttribute("client", this.clientService.getById(id));
        }
        return "client/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute Client client, final BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
         
        Client existingByEmail = this.clientService.getByEmail(client.getEmail());
        if (existingByEmail != null && existingByEmail.getId() != id) {
            bindingResult.addError(new FieldError("client", "email", client.getEmail(), false, null, null, "The e-mail address is already in use"));
        }
                
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.client", bindingResult);
            redirectAttributes.addFlashAttribute("client", client);
            if (!redirectAttributes.containsAttribute("id")) {
                redirectAttributes.addAttribute("id", id);
            }
            return "redirect:/client/{id}/edit";
        }
        
        Client newClient = this.clientService.edit(id, client);
        redirectAttributes.addAttribute("id", newClient.getId());
        return "redirect:/client/{id}/details";
    }
    
}