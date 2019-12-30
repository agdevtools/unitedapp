package com.football.unitedapp;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Import(AspectConfig.class)
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
}
