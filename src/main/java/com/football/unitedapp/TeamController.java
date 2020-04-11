package com.football.unitedapp;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Import(AspectConfig.class)
@Lazy
@RestController
class TeamController {

    private TeamServiceImpl teamServiceImpl;


    public TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }

    @GetMapping("/team")
    public List<TeamEntity> getTeam()
    {
        List<TeamEntity> result = teamServiceImpl.getTeam();
        return result;
    }

    @PostMapping("/newplayer")
    public TeamEntity createPlayer(@RequestBody TeamEntity player)
    {
       teamServiceImpl.createPlayer(player);
       return player;
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
        TeamEntity result = teamServiceImpl.getPlayer(playerId);
        return result;

    }

    @RequestMapping(value = "/league", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public String getLeagueTable()
    {
        ResponseEntity responseEntity =  teamServiceImpl.getLeagueTable();
        String response = responseEntity.getBody().toString();
        return response;
    }

    @DeleteMapping(value = "/delete/{playerId}")
    public void deletePlayer(int playerId) {
        teamServiceImpl.deleteByPlayerId(playerId);
    }

    @PostConstruct
    public void writePostConstruct()
    { System.out.println("****** Post Construct on Controller Bean ******"); }

    @PreDestroy
    public void writePreDestroyMessage()
    { System.out.println("****** Pre Destroy on Controller Bean ******"); }
}
