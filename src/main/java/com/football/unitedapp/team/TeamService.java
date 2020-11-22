package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface TeamService {

    TeamResponse getTeam();

    TeamResponse getPlayer(int playerId);

    TeamResponse createPlayer(TeamEntity teamEntity);

    TeamResponse updatePlayer(TeamEntity teamEntity);

    ResponseEntity getLeagueTable();

    HttpStatus deleteByPlayerId(int playerId);
}
