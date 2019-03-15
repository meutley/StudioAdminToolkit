package com.meutley.studioadmintoolkit.studiosession;

import java.util.Arrays;
import java.util.List;

import com.meutley.studioadmintoolkit.client.Client;
import com.meutley.studioadmintoolkit.client.ClientDto;
import com.meutley.studioadmintoolkit.client.ClientRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudioSessionServiceImpl implements StudioSessionService {

    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;
    private final StudioSessionRepository studioSessionRepository;

    public StudioSessionServiceImpl(
        ModelMapper modelMapper,
        ClientRepository clientRepository,
        StudioSessionRepository studioSessionRepository
    ) {
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.studioSessionRepository = studioSessionRepository;
    }

    @Override
    public StudioSessionDto create(StudioSessionDto studioSession, ClientDto client) {
        studioSession.setClient(client);
        StudioSession studioSessionEntity = studioSessionRepository.save(modelMapper.map(studioSession, StudioSession.class));
        return modelMapper.map(studioSessionEntity, StudioSessionDto.class);
    }

    @Override
    public StudioSessionDto edit(int id, int clientId, StudioSessionDto studioSession) {
        StudioSession existingEntity = this.studioSessionRepository.getOne(id);
        Client client = this.clientRepository.getOne(clientId);
        existingEntity.setClient(client);
        existingEntity.setName(studioSession.getName());
        this.studioSessionRepository.save(existingEntity);
        return modelMapper.map(existingEntity, StudioSessionDto.class);
    }
    
    @Override
    public List<StudioSessionDto> getByClientId(int clientId) {
        List<StudioSession> studioSessions = studioSessionRepository.getByClientId(clientId);
        return Arrays.asList(modelMapper.map(studioSessions, StudioSessionDto[].class));
    }
    
    @Override
    public StudioSessionDto getById(int id) {
        return modelMapper.map(this.studioSessionRepository.getOne(id), StudioSessionDto.class);
    }
    
}