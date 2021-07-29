package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface TeamService {

    TeamResponse getTeam();

    TeamResponse getPlayer(int playerId);

    TeamResponse createPlayer(TeamRequest teamRequest);

    TeamResponse updatePlayer(TeamEntity teamEntity);

    ResponseEntity<String> getLeagueTable();

    HttpStatus deleteByPlayerId(int playerId);
}
