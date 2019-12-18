package com.football.unitedapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/team/id/{playerId}/name/{playerName}")
    public TeamEntity createPlayer(@PathVariable(value="playerId") int playerId, @PathVariable(value="playerName") String playerName)
    {
       TeamEntity teamEntity = new TeamEntity(playerId,playerName);
       teamServiceImpl.createPlayer(teamEntity);
       return teamEntity;
    }


    @GetMapping("/team/{playerId}")
    public TeamEntity getPlayer(@PathVariable(value="playerId") int playerId)
    {
        TeamEntity result = teamServiceImpl.getPlayer(playerId);
        return result;
    }


}
