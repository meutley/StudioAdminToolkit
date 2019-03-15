package com.meutley.studioadmintoolkit.studiosession;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.meutley.studioadmintoolkit.client.ClientDto;

public class StudioSessionDetailsViewModel {

    private List<ClientDto> clients;
    @Valid
    private StudioSessionDto studioSession;
    private Optional<Integer> selectedClientId;

    public static class Builder {

        private List<ClientDto> builderClients;
        private StudioSessionDto builderStudioSession;
        private Optional<Integer> builderSelectedClientId;

        public Builder clients(List<ClientDto> clients) {
            this.builderClients = clients;
            return this;
        }

        public Builder studioSession(StudioSessionDto studioSession) {
            this.builderStudioSession = studioSession;
            return this;
        }

        public Builder selectedClientId(Optional<Integer> selectedClientId) {
            this.builderSelectedClientId = selectedClientId;
            return this;
        }

        public StudioSessionDetailsViewModel build() {
            StudioSessionDetailsViewModel viewModel = new StudioSessionDetailsViewModel();
            viewModel.setClients(this.builderClients);
            viewModel.setStudioSession(this.builderStudioSession);
            viewModel.setSelectedClientId(this.builderSelectedClientId);
            return viewModel;
        }
        
    }

    public List<ClientDto> getClients() {
        return this.clients;
    }

    @Valid
    public StudioSessionDto getStudioSession() {
        return this.studioSession;
    }

    public Optional<Integer> getSelectedClientId() {
        return this.selectedClientId;
    }

    public void setClients(List<ClientDto> clients) {
        this.clients = clients;
    }

    public void setStudioSession(StudioSessionDto studioSession) {
        this.studioSession = studioSession;
    }

    public void setSelectedClientId(Optional<Integer> selectedClientId) {
        this.selectedClientId = selectedClientId;
    }
    
}