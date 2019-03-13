package com.meutley.studioadmintoolkit.studiosession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.meutley.studioadmintoolkit.client.ClientDto;

public class ListStudioSessionViewModel {

    private Optional<Integer> selectedClientId = Optional.empty();
    private List<ClientDto> clients = new ArrayList<>();
    private List<StudioSessionDto> studioSessions = new ArrayList<>();

    public static class Builder {

        private Optional<Integer> builderSelectedClientId = Optional.empty();
        private List<ClientDto> builderClients = new ArrayList<>();
        private List<StudioSessionDto> builderStudioSessions = new ArrayList<>();

        public Builder selectedClientId(Optional<Integer> selectedClientId) {
            this.builderSelectedClientId = selectedClientId;
            return this;
        }

        public Builder clients(List<ClientDto> clients) {
            this.builderClients = clients;
            return this;
        }

        public Builder studioSessions(List<StudioSessionDto> studioSessions) {
            this.builderStudioSessions = studioSessions;
            return this;
        }

        public ListStudioSessionViewModel build() {
            ListStudioSessionViewModel viewModel = new ListStudioSessionViewModel();
            viewModel.setSelectedClientId(this.builderSelectedClientId);
            viewModel.setClients(this.builderClients);
            viewModel.setStudioSessions(this.builderStudioSessions);
            return viewModel;
        }
        
    }
    
    public Optional<Integer> getSelectedClientId() {
        return this.selectedClientId;
    }

    public List<ClientDto> getClients() {
        return this.clients;
    }

    public List<StudioSessionDto> getStudioSessions() {
        return this.studioSessions;
    }

    public void setSelectedClientId(Optional<Integer> selectedClientId) {
        this.selectedClientId = selectedClientId;
    }

    public void setClients(List<ClientDto> clients) {
        this.clients = clients;
    }

    public void setStudioSessions(List<StudioSessionDto> studioSessions) {
        this.studioSessions = studioSessions;
    }

    // View methods

    public String getCreateSessionLink() {
        return this.selectedClientId.isEmpty()
            ? "/studio-session/create"
            : String.format("/studio-session/create?clientId=%s", selectedClientId.get());
    }

    public boolean hasStudioSessions() {
        return this.studioSessions != null && this.studioSessions.size() > 0;
    }

    public boolean isClientSelected() {
        return this.selectedClientId.isPresent();
    }

    public boolean shouldShowNoSessionsMessage() {
        return this.isClientSelected() && this.studioSessions.isEmpty();
    }

    public boolean shouldShowSelectClientMessage() {
        return !this.isClientSelected();
    }
    
}