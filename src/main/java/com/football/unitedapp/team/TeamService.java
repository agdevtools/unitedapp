package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamService {

    List<TeamEntity> getTeam();

    TeamEntity getPlayer(int playerId);

    TeamResponse createPlayer(TeamEntity teamEntity);

    ResponseEntity getLeagueTable();

    HttpStatus deleteByPlayerId(int playerId);
}
