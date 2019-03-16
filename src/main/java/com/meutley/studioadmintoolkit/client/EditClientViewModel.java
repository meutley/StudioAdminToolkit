package com.meutley.studioadmintoolkit.client;

import javax.validation.Valid;

public class EditClientViewModel {

    @Valid
    private ClientDto client;
    
    private boolean hasMailingAddress = false;

    public ClientDto getClient() {
        return this.client;
    }
    
    public boolean getHasMailingAddress() {
        return this.hasMailingAddress;
    }

    public int getMailingAddressId() {
        return this.client.getMailingAddress() != null
            ? this.client.getMailingAddress().getId()
            : 0;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public void setHasMailingAddress(boolean hasMailingAddress) {
        this.hasMailingAddress = hasMailingAddress;
    }
    
}