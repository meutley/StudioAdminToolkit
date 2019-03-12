package com.meutley.studioadmintoolkit.studiosession;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.client.Client;
import com.meutley.studioadmintoolkit.client.ClientService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/studio-session")
public class StudioSessionController {

    private final ClientService clientService;
    private final StudioSessionService studioSessionService;

    public StudioSessionController(
        ClientService clientService,
        StudioSessionService studioSessionService
    ) {
        this.clientService = clientService;
        this.studioSessionService = studioSessionService;
    }
    
    @GetMapping(value = { "", "/index" })
    public String index(Model model) {
        return "redirect:/studio-session/list";
    }

    @GetMapping(value = { "/list", "/list/{clientId}" })
    public String list(@PathVariable Optional<Integer> clientId, Model model) {
        boolean hasClientId = clientId.isPresent();
        model.addAttribute("isClientSelected", hasClientId);

        List<Client> clients = this.clientService.getAll();
        clients.sort(Comparator.comparing(c -> c.getName()));
        model.addAttribute("clients", clients);
        
        if (hasClientId) {
            List<StudioSession> studioSessions = this.studioSessionService.getByClientId(clientId.get());
            model.addAttribute("studioSessions", studioSessions);
            model.addAttribute("selectedClientId", clientId.get());
        }
        return "studio-session/list";
    }

    @PostMapping("/list/{clientId}")
    public String list(@PathVariable int clientId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("clientId", clientId);
        return "redirect:/studio-session/list/{clientId}";
    }

    @GetMapping("/{clientId}/create")
    public String create(@PathVariable int clientId, Model model) {
        if (!model.containsAttribute("client")) {
            model.addAttribute("client", this.clientService.getById(clientId));
        }
        if (!model.containsAttribute("studioSession")) {
            model.addAttribute("studioSession", new StudioSession());
        }
        if (!model.containsAttribute("clientId")) {
            model.addAttribute("clientId", clientId);
        }
        return "studio-session/create";
    }
    
}