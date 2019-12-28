package com.football.unitedapp;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class TeamController {

    private TeamServiceImpl teamServiceImpl;

    @Autowired
    private MeterRegistry meterRegistry;


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

        meterRegistry.counter("players.searched.on",
                "playerId", Integer.toString(playerId),
                        "Team", "Manchester United")
                        .increment();

        DistributionSummary summary;
        summary = DistributionSummary.builder("players.name.summary").register(meterRegistry);
        summary.record(playerId);

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


}
