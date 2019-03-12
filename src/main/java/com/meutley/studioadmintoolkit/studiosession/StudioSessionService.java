package com.meutley.studioadmintoolkit.studiosession;

import java.util.List;

public interface StudioSessionService {

    List<StudioSession> getByClientId(int clientId);
    
}