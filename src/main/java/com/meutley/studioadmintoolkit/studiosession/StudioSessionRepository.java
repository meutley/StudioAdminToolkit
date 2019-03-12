package com.meutley.studioadmintoolkit.studiosession;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioSessionRepository extends JpaRepository<StudioSession, Integer> {
    
    @Query(value = "SELECT * FROM studio_session WHERE client_id = ?1", nativeQuery = true)
    List<StudioSession> getByClientId(int clientId);
    
}