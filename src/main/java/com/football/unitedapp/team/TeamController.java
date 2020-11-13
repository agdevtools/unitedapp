package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.util.AspectConfig;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Objects;

@Import(AspectConfig.class)
@Lazy
@RestController
public class TeamController {

    final TeamServiceImpl teamServiceImpl;

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
    public TeamEntity createPlayer(@RequestBody TeamRequest teamRequest)
    {
       TeamEntity  teamEntity = TeamEntity.builder()
               .playerId(teamRequest.getPlayerId())
               .playerName(teamRequest.getPlayerName())
               .build();

       return teamServiceImpl.createPlayer(teamEntity);
    }

    @PutMapping("/team/{playerName}/player/{playerId}")
    public ResponseEntity<TeamEntity> updatePlayer(@PathVariable(value="playerName") String playerName,
                                                   @PathVariable(value="playerId") Integer playerId
                                                   )

    {
        TeamEntity playerUpdated = teamServiceImpl.savePlayer(playerId, playerName);
        return new ResponseEntity<TeamEntity>(playerUpdated, HttpStatus.OK);
    }

    @GetMapping("/team/{playerId}")
    public TeamEntity getPlayer(@PathVariable(value="playerId") Integer playerId)
    {
        return teamServiceImpl.getPlayer(playerId);
    }

    @RequestMapping(value = "/league", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public String getLeagueTable() {
        ResponseEntity<String> responseEntity =  teamServiceImpl.getLeagueTable();
        return Objects.requireNonNull(responseEntity.getBody());
    }

    @DeleteMapping(value = "/team/{playerId}")
    public void deletePlayer(Integer playerId) {
        teamServiceImpl.deleteByPlayerId(playerId);
    }

    @PostConstruct
    public void writePostConstruct() {
        System.out.println("****** Post Construct on Controller Bean ******");
    }

    @PreDestroy
    public void writePreDestroyMessage() {
        System.out.println("****** Pre Destroy on Controller Bean ******");
    }
}
