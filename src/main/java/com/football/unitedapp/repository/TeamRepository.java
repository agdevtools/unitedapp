package com.football.unitedapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {
    List<TeamEntity> findAll();
    TeamEntity findByPlayerId(int playerId);
    TeamEntity save(TeamEntity teamEntity);

    @Transactional
    void deleteByPlayerId(int playerId);
}

