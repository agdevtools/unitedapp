package com.football.unitedapp;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class TeamController {

    private TeamServiceImpl teamServiceImpl;

    @Autowired
    private MeterRegistry meterRegistry;


    public TeamController(TeamServiceImpl teamServiceImpl, MeterRegistry meter) {
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
    public TeamEntity getPlayer(@PathVariable(value="playerId") Integer playerId)
    {

        meterRegistry.counter("players.searched.on",
                "playerId", Integer.toString(playerId))
                        .increment();

        DistributionSummary summary;
        summary = DistributionSummary.builder("players.name.summary").register(meterRegistry);
        summary.record(playerId);

        TeamEntity result = teamServiceImpl.getPlayer(playerId);

        return result;

    }


}
