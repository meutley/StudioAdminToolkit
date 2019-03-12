package com.meutley.studioadmintoolkit.studiosession;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudioSessionServiceImpl implements StudioSessionService {

    private final StudioSessionRepository studioSessionRepository;

    public StudioSessionServiceImpl(StudioSessionRepository studioSessionRepository) {
        this.studioSessionRepository = studioSessionRepository;
    }

    @Override
    public StudioSession create(StudioSession studioSession) {
        return studioSessionRepository.save(studioSession);
    }

    @Override
    public List<StudioSession> getByClientId(int clientId) {
        return studioSessionRepository.getByClientId(clientId);
    }
    
}