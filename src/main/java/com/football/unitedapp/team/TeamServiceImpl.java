package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;


    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<TeamEntity> getTeam() {
        return teamRepository.findAll();
    }

    @Override
    public TeamEntity getPlayer(int playerId) {
        return teamRepository.findByPlayerId(playerId);
    }

    @Override
    public TeamEntity createPlayer(TeamEntity teamEntity) {
      teamRepository.save(teamEntity);
      return teamEntity;
    }

    public TeamEntity createPlayer() {
        //default constructor generates Luke Shaw
        TeamEntity teamEntity = new TeamEntity(23,"Luke Shaw");
        return teamRepository.save(teamEntity);
    }

    @Override
    public ResponseEntity<String> getLeagueTable() {
            final String uri = "https://api.football-data.org/v2/competitions/PL/standings";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("X-Auth-Token","2a88122678894952829ef98dd6e898f6");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            return result;

    }

    public void deleteByPlayerId(int playerId) {
        teamRepository.deleteByPlayerId(playerId);
    }

    public TeamEntity savePlayer (Integer playerId, String playerName) {
        TeamEntity teamEntity = new TeamEntity(playerId, playerName);
        return  teamRepository.save(teamEntity);
    }
}