package com.meutley.studioadmintoolkit.studiosession;

import java.util.List;

import com.meutley.studioadmintoolkit.client.ClientDto;

public interface StudioSessionService {

    StudioSessionDto create(StudioSessionDto studioSession, ClientDto client);
    StudioSessionDto edit(int id, int clientId, StudioSessionDto studioSession);
    List<StudioSessionDto> getByClientId(int clientId);
    StudioSessionDto getById(int id);
    
}