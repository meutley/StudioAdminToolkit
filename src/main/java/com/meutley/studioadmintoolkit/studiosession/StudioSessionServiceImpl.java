package com.meutley.studioadmintoolkit.studiosession;

import java.util.Arrays;
import java.util.List;

import com.meutley.studioadmintoolkit.client.ClientDto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudioSessionServiceImpl implements StudioSessionService {

    private final ModelMapper modelMapper;
    private final StudioSessionRepository studioSessionRepository;

    public StudioSessionServiceImpl(
        ModelMapper modelMapper,
        StudioSessionRepository studioSessionRepository
    ) {
        this.modelMapper = modelMapper;
        this.studioSessionRepository = studioSessionRepository;
    }

    @Override
    public StudioSessionDto create(StudioSessionDto studioSession, ClientDto client) {
        studioSession.setClient(client);
        StudioSession studioSessionEntity = studioSessionRepository.save(modelMapper.map(studioSession, StudioSession.class));
        return modelMapper.map(studioSessionEntity, StudioSessionDto.class);
    }

    @Override
    public List<StudioSessionDto> getByClientId(int clientId) {
        List<StudioSession> studioSessions = studioSessionRepository.getByClientId(clientId);
        return Arrays.asList(modelMapper.map(studioSessions, StudioSessionDto[].class));
    }
    
}