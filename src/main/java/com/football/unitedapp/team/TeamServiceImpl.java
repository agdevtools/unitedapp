package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.util.ErrorDetails;
import com.football.unitedapp.util.UnitedErrorHandler;
import com.football.unitedapp.util.ValidationError;
import com.football.unitedapp.util.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    @Autowired
    final TeamRepository teamRepository;


    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public TeamResponse getTeam() {
         List<TeamEntity> teamEntity = teamRepository.findAll();
         return new TeamResponse("200", teamEntity);
    }

    @Override
    public TeamResponse getPlayer(int playerId) {
        List<TeamEntity> teamEntity = teamRepository.findByPlayerId(playerId);
        if (!teamEntity.isEmpty()) {
            return new TeamResponse("200", teamEntity);
        }
        else return new TeamResponse("404 Not Found",teamEntity);
    }

    @Override
    public TeamResponse createPlayer(TeamRequest teamRequest) {
            validateTeamRequest(teamRequest);
            List<TeamEntity> listOfTeamEntity = new ArrayList<TeamEntity>();
            listOfTeamEntity.add(teamRepository.save(new TeamEntity(teamRequest.getPlayerId(),teamRequest.getPlayerName())));
            return new TeamResponse("201",listOfTeamEntity);
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
        List<TeamEntity> listOfTeamEntity = new ArrayList<TeamEntity>();
        listOfTeamEntity.add(teamRepository.save(teamEntity));
        return new TeamResponse("201",listOfTeamEntity);
    }

    public TeamResponse updatePlayer(TeamEntity teamEntity) {
        List<TeamEntity> listOfTeamEntity = new ArrayList<TeamEntity>();
        teamRepository.save(teamEntity);
        return new TeamResponse("200",listOfTeamEntity);
    }

    public boolean validateTeamRequest(TeamRequest teamRequest) {
        List<ErrorDetails> errorDetailsList = new ArrayList<>();

        if(!validatePlayerId(teamRequest.getPlayerId())) {
            errorDetailsList.add(populateErrorDetails("Player Id","PlayerId must be a number greater than zero"));
        }

        if(!validatePlayerName(teamRequest.getPlayerName())) {
            errorDetailsList.add(populateErrorDetails("Player Name", "Player Name cannot contain numbers and certain special characters"));
        }

        if (errorDetailsList.size() > 0) {
            ValidationError error = new ValidationError("Validationfailed", "There were valodation errors", errorDetailsList);
            throw new ValidationException(400, error);
        }

        return true;
    }

    private ErrorDetails populateErrorDetails(String target, String message) {
        return new ErrorDetails("invalid",target,message);
    }

    private boolean validatePlayerId(int playerId) {
        return playerId >0 && Integer.toString(playerId).matches("^\\d+$");
    }

    private boolean validatePlayerName(String playerName) {
        return !playerName.isEmpty() && playerName.matches(("^[a-zA-Z .'-]*$"));
    }
}
