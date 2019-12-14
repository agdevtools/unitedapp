package com.football.unitedapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class TeamController {

    @Autowired
    TeamServiceImpl teamServiceImpl;

    @GetMapping("/team")
    public List<TeamEntity> getTeam()
    {
        List<TeamEntity> result = teamServiceImpl.getTeam();
        return result;
    }

    @GetMapping("/team/add")
    public void createPlayer()
    {
        teamServiceImpl.createPlayer();
    }

    @GetMapping("/team/{playerId}")
    public List<TeamEntity> getPlayer(@PathVariable(value="playerId") int playerId)
    {
        List<TeamEntity> result = teamServiceImpl.getPlayer(playerId);
        return result;
    }


}
