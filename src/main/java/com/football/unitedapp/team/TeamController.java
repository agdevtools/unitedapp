package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.util.AspectConfig;
import com.football.unitedapp.util.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Import(AspectConfig.class)
@RestController
public class TeamController {


    final TeamServiceImpl teamServiceImpl;

    @Autowired
    public TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }

    @GetMapping("/team")
    public TeamResponse getTeam()
    {
        return teamServiceImpl.getTeam();
    }

    @PostMapping("/team")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse createPlayer(@RequestBody TeamRequest teamRequest)
    {
        //          TeamEntity expectedTeamEntity = new TeamEntity(teamRequest.getPlayerId(), teamRequest.getPlayerName());
        //          List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        //          expectedTeamEntityList.add(expectedTeamEntity);
        //          return new TeamResponse("404 Invalid request",expectedTeamEntityList);
        if (validateTeamRequest(teamRequest)) {
          return teamServiceImpl.createPlayer(new TeamEntity(teamRequest.getPlayerId(),teamRequest.getPlayerName()));
      }
      else {
            throw new ErrorHandler.BadRequestException();
        }
  }

    @PutMapping("/team")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponse updatePlayer(@RequestBody TeamRequest teamRequest) {
        TeamEntity  teamEntity = new TeamEntity(teamRequest.getPlayerId(), teamRequest.getPlayerName());
        return teamServiceImpl.updatePlayer(teamEntity);
    }

    @GetMapping("/team/{playerId}")
    public TeamResponse getPlayer(@PathVariable(value="playerId") Integer playerId)
    {
        return teamServiceImpl.getPlayer(playerId);
    }

    @RequestMapping(value = "/league", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public String getLeagueTable() {
        ResponseEntity<String> responseEntity =  teamServiceImpl.getLeagueTable();
        return Objects.requireNonNull(responseEntity.getBody());
    }

    @DeleteMapping(value = "/team/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deletePlayer(@PathVariable(value="playerId") Integer playerId) {
        return teamServiceImpl.deleteByPlayerId(playerId);
    }

    public boolean validateTeamRequest(TeamRequest teamRequest) {
        return validatePlayerId(teamRequest.getPlayerId()) && validatePlayerName(teamRequest.getPlayerName());
    }

    private boolean validatePlayerId(int playerId) {
        return playerId >0 && Integer.toString(playerId).matches("^\\d+$");
    }

    private boolean validatePlayerName(String playerName) {
        return !playerName.isEmpty() && playerName.matches(("^[a-zA-Z .'-]*$"));
    }
}
