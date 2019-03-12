package com.meutley.studioadmintoolkit.studiosession;

import java.util.List;

public interface StudioSessionService {

    StudioSession create(StudioSession studioSession);
    List<StudioSession> getByClientId(int clientId);
    
}