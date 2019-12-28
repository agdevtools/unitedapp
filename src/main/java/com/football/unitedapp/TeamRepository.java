package com.football.unitedapp;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<TeamEntity, String> {
    List<TeamEntity> findAll();
    TeamEntity findByPlayerId(int playerId);
    TeamEntity save(TeamEntity teamEntity);
    void deleteByPlayerId(int playerId);
}

