package com.meutley.studioadmintoolkit.studiosession;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.meutley.studioadmintoolkit.client.Client;

public class CreateStudioSessionViewModel {

    private List<Client> clients;
    @Valid
    private StudioSession studioSession;
    private Optional<Integer> selectedClientId;

    public static class Builder {

        private List<Client> builderClients;
        private StudioSession builderStudioSession;
        private Optional<Integer> builderSelectedClientId;

        public Builder clients(List<Client> clients) {
            this.builderClients = clients;
            return this;
        }

        public Builder studioSession(StudioSession studioSession) {
            this.builderStudioSession = studioSession;
            return this;
        }

        public Builder selectedClientId(Optional<Integer> selectedClientId) {
            this.builderSelectedClientId = selectedClientId;
            return this;
        }

        public CreateStudioSessionViewModel build() {
            CreateStudioSessionViewModel viewModel = new CreateStudioSessionViewModel();
            viewModel.setClients(this.builderClients);
            viewModel.setStudioSession(this.builderStudioSession);
            viewModel.setSelectedClientId(this.builderSelectedClientId);
            return viewModel;
        }
        
    }

    public List<Client> getClients() {
        return this.clients;
    }

    @Valid
    public StudioSession getStudioSession() {
        return this.studioSession;
    }

    public Optional<Integer> getSelectedClientId() {
        return this.selectedClientId;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setStudioSession(StudioSession studioSession) {
        this.studioSession = studioSession;
    }

    public void setSelectedClientId(Optional<Integer> selectedClientId) {
        this.selectedClientId = selectedClientId;
    }
    
}