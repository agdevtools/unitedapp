package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

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
    public List<TeamEntity> getTeam() {
        return teamRepository.findAll();
    }

//    public TeamResponse getTeam2() {
//        Map<String, List<TeamEntity>> map = convert2(teamRepository.findAll());
//        return TeamResponse(200, map);
//    }
//
//    private Map<String, List<TeamEntity>> convert2(List<TeamEntity> entitylistIn) {
//        List<TeamEntity> entitylist = entitylistIn;
//        List<TeamEntity> mapmodelist = entitylistIn;
//        for (TeamEntity teamEntity; entitylist;) {
//            mapmodelist.add()
//        }
//    }


    @Override
    public TeamEntity getPlayer(int playerId) {
        return teamRepository.findByPlayerId(playerId);
    }

    @Override
    public TeamResponse createPlayer(TeamEntity teamEntity) {
      teamRepository.save(teamEntity);
      return new TeamResponse(HttpStatus.CREATED,teamEntity.playerId,teamEntity.playerName);
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

    public TeamResponse savePlayer (TeamEntity teamEntity) {
        teamRepository.save(teamEntity);
        return new TeamResponse(HttpStatus.OK,teamEntity.playerId,teamEntity.playerName);
    }
}
