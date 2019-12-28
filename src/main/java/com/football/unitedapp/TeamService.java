package com.football.unitedapp;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamService {

    List<TeamEntity> getTeam();

    TeamEntity getPlayer(int playerId);

    TeamEntity createPlayer(TeamEntity teamEntity);

    ResponseEntity getLeagueTable();

    void deleteByPlayerId(int playerId);
}
