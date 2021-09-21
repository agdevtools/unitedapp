package com.football.unitedapp.team;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.util.AspectConfig;
import com.football.unitedapp.util.S3Service;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Import(AspectConfig.class)
@RestController
public class TeamController {


    final TeamServiceImpl teamServiceImpl;

    @Autowired
    private S3Service s3Service;

    @Autowired
    public TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }

    @GetMapping("/team")
    public List<TeamEntity> getTeam()
    {
        return  teamServiceImpl.getTeam();
    }

    @PostMapping("/team")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse createPlayer(@RequestBody TeamRequest teamRequest)
    {
      if (validateTeamRequest(teamRequest)) {
          return teamServiceImpl.createPlayer(new TeamEntity(teamRequest.getPlayerId(), teamRequest.getPlayerName()));
      }
      else {
          return new TeamResponse(HttpStatus.BAD_REQUEST, teamRequest.getPlayerId(), teamRequest.getPlayerName());
      }
    }

    @PutMapping("/team")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponse updatePlayer(@RequestBody TeamRequest teamRequest) {
        TeamEntity  teamEntity = new TeamEntity(teamRequest.getPlayerId(), teamRequest.getPlayerName());

        return teamServiceImpl.savePlayer(teamEntity);
    }

    @GetMapping("/team/{playerId}")
    public TeamEntity getPlayer(@PathVariable(value="playerId") Integer playerId)
    {
        return teamServiceImpl.getPlayer(playerId);
    }

    @GetMapping("/s3")
    public String getBuckets()
    {
        return s3Service.getBuckets();
    }

    @GetMapping("/s3File")
    public String getFile() throws IOException {
        return s3Service.getFile();
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
