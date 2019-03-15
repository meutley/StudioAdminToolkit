package com.meutley.studioadmintoolkit.studiosession;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.meutley.studioadmintoolkit.client.ClientDto;
import com.meutley.studioadmintoolkit.client.ClientService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<StudioSessionDto> studioSessions = new ArrayList<>();
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
            StudioSessionDetailsViewModel viewModel = new StudioSessionDetailsViewModel.Builder()
                .clients(this.getClientsForList())
                .studioSession(new StudioSessionDto())
                .selectedClientId(Optional.empty())
                .build();
            model.addAttribute("viewModel", viewModel);
        }
        return "studio-session/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute StudioSessionDetailsViewModel viewModel, final BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel", bindingResult);
            viewModel.setClients(this.getClientsForList());
            redirectAttributes.addFlashAttribute("viewModel", viewModel);
            return "redirect:/studio-session/create";
        }

        StudioSessionDto studioSession = viewModel.getStudioSession();
        ClientDto client = this.clientService.getById(viewModel.getSelectedClientId().get());
        this.studioSessionService.create(studioSession, client);
        return String.format("redirect:/studio-session/list?clientId=%s", viewModel.getSelectedClientId().get());
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable int id, Model model) {
        StudioSessionDto studioSession = this.studioSessionService.getById(id);
        model.addAttribute("studioSession", studioSession);
        return "studio-session/details";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        if (!model.containsAttribute("viewModel")) {
            StudioSessionDto studioSession = this.studioSessionService.getById(id);
            StudioSessionDetailsViewModel viewModel = new StudioSessionDetailsViewModel.Builder()
                .clients(this.getClientsForList())
                .studioSession(studioSession)
                .selectedClientId(Optional.of(studioSession.getClient().getId()))
                .build();
            model.addAttribute("viewModel", viewModel);
        }
        return "studio-session/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@Valid @ModelAttribute StudioSessionDetailsViewModel viewModel, final BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.viewModel", bindingResult);
            viewModel.setClients(this.getClientsForList());
            redirectAttributes.addFlashAttribute("viewModel", viewModel);
            redirectAttributes.addAttribute("id", viewModel.getStudioSession().getId());
            return "redirect:/studio-session/{id}/edit";
        }

        StudioSessionDto studioSession = viewModel.getStudioSession();
        ClientDto client = this.clientService.getById(viewModel.getSelectedClientId().get());
        this.studioSessionService.edit(studioSession.getId(), client.getId(), studioSession);
        return "redirect:/studio-session/list";
    }

    private final List<ClientDto> getClientsForList() {
        List<ClientDto> clients = this.clientService.getAll();
        clients.sort(Comparator.comparing(c -> c.getName()));
        return clients;
    }
    
}