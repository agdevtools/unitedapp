package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TeamServiceImpl implements TeamService {

    @Autowired
    final TeamRepository teamRepository;


    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public TeamResponseTest getTeam() {
         List<TeamEntity> teamEntity = teamRepository.findAll();
         return new TeamResponseTest("200", teamEntity);
    }

    @Override
    public TeamResponseTest getPlayer(int playerId) {
        List<TeamEntity> teamEntity = teamRepository.findByPlayerId(playerId);
        if (!teamEntity.isEmpty()) {
            return new TeamResponseTest("200", teamEntity);
        }
        else return new TeamResponseTest("404 Not Found",teamEntity);
    }

    @Override
    public TeamResponseTest createPlayer(TeamEntity teamEntity) {
        List<TeamEntity> listOfTeamEntity = new ArrayList<TeamEntity>();
        listOfTeamEntity.add(teamRepository.save(teamEntity));
      return new TeamResponseTest("201",listOfTeamEntity);
    }

    @Override
    public ResponseEntity<String> getLeagueTable() {
            final String uri = "https://api.football-data.org/v2/competitions/PL/standings";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("X-Auth-Token","2a88122678894952829ef98dd6e898f6");
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

    }

    public HttpStatus deleteByPlayerId(int playerId) {
        teamRepository.deleteByPlayerId(playerId);
        return HttpStatus.NO_CONTENT;
    }

    public TeamResponseTest savePlayer (TeamEntity teamEntity) {
        List<TeamEntity> listOfTeamEntity = new ArrayList<TeamEntity>();
        listOfTeamEntity.add(teamRepository.save(teamEntity));
        return new TeamResponseTest("201",listOfTeamEntity);
    }

    public TeamResponseTest updatePlayer(TeamEntity teamEntity) {
        List<TeamEntity> listOfTeamEntity = new ArrayList<TeamEntity>();
        teamRepository.save(teamEntity);
        return new TeamResponseTest("200",listOfTeamEntity);
    }
}
