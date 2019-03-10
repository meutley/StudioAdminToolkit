package com.meutley.studioadmintoolkit.client;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {

        this.clientService = clientService;
    }

    @GetMapping(value = { "", "/index" })
    public String index() {
        
        return "client/index";
    }

    @GetMapping("/{id}/details")
    public ModelAndView details(@PathVariable int id) {
        
        Client client = this.clientService.getById(id);
        ModelAndView view = new ModelAndView("client/details");
        view.addObject("client", client);
        return view;
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
                
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.client", bindingResult);
            redirectAttributes.addFlashAttribute("client", client);
            return "redirect:/client/create";
        }
        
        Client newClient = this.clientService.create(client);
        redirectAttributes.addAttribute("id", newClient.getId());
        return "redirect:/client/details/{id}";
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
                
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.client", bindingResult);
            redirectAttributes.addFlashAttribute("client", client);
            redirectAttributes.addAttribute(client.getId());
            return "redirect:/client/edit/{id}";
        }
        
        Client newClient = this.clientService.edit(id, client);
        redirectAttributes.addAttribute("id", newClient.getId());
        return "redirect:/client/details/{id}";
    }
    
}