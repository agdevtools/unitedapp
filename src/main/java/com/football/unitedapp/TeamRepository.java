package com.football.unitedapp;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<TeamEntity, String> {
    List<TeamEntity> findAll();
    List<TeamEntity> findByPlayerId(int playerId);
}

