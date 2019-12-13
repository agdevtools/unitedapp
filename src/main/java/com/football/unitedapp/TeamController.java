package com.football.unitedapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

@Autowired
TeamRepository teamRepository;

    @GetMapping("/team")
    public List<TeamEntity> getTeam()
    {
        List<TeamEntity> result = teamRepository.findAll();
        return result;
    }

    @GetMapping("/team/add")
    public void createPlayer()
    {
        TeamEntity teamEntity = new TeamEntity(2,"Victor Lindelof");
        teamRepository.save(teamEntity);
    }

    @GetMapping("/team/{playerId}")
    public List<TeamEntity> getPlayer(@PathVariable(value="playerId") int playerId)
    {
        List<TeamEntity> result = teamRepository.findByPlayerId(playerId);
        return result;
    }


}
