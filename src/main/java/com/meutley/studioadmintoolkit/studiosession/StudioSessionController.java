package com.meutley.studioadmintoolkit.studiosession;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.meutley.studioadmintoolkit.client.Client;
import com.meutley.studioadmintoolkit.client.ClientService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(value = { "/list" })
    public String list(@RequestParam("clientId") Optional<Integer> clientId, Model model) {
        List<StudioSession> studioSessions = new ArrayList<>();
        if (clientId.isPresent()) {
            studioSessions.addAll(this.studioSessionService.getByClientId(clientId.get()));
        }
        
        ListStudioSessionViewModel viewModel = new ListStudioSessionViewModel.Builder()
            .clients(this.getClientsForList())
            .selectedClientId(clientId)
            .studioSessions(studioSessions)
            .build();
        model.addAttribute("viewModel", viewModel);
        
        return "studio-session/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute("viewModel")) {
            CreateStudioSessionViewModel viewModel = new CreateStudioSessionViewModel.Builder()
                .clients(this.getClientsForList())
                .studioSession(new StudioSession())
                .selectedClientId(Optional.empty())
                .build();
            model.addAttribute("viewModel", viewModel);
        }
        return "studio-session/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute CreateStudioSessionViewModel viewModel, final BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel", bindingResult);
            viewModel.setClients(this.getClientsForList());
            redirectAttributes.addFlashAttribute("viewModel", viewModel);
            return "redirect:/studio-session/create";
        }

        StudioSession studioSession = viewModel.getStudioSession();
        Client client = this.clientService.getById(viewModel.getSelectedClientId().get());
        studioSession.setClient(client);
        this.studioSessionService.create(studioSession);
        return String.format("redirect:/studio-session/list?clientId=%s", viewModel.getSelectedClientId().get());
    }

    private final List<Client> getClientsForList() {
        List<Client> clients = this.clientService.getAll();
        clients.sort(Comparator.comparing(c -> c.getName()));
        return clients;
    }
    
}