package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class TeamServiceImpl implements TeamService {

    final TeamRepository teamRepository;

    @Autowired
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
            checkIfPlayerExists(teamRequest, "create");
            List<TeamEntity> listOfTeamEntity = new ArrayList<>();
            listOfTeamEntity.add(teamRepository.save(new TeamEntity(teamRequest.getPlayerId(), teamRequest.getPlayerName(),teamRequest.getPlayerPosition())));
            return new TeamResponse("201", listOfTeamEntity);
    }

    @Override
    public ResponseEntity<String> getLeagueTable() {
            final String uri = "https://api.football-data.org/v2/competitions/PL/standings";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("X-Auth-Token","2a88122678894952829ef98dd6e898f6");
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    public HttpStatus deleteByPlayerId(int playerId) {
        teamRepository.deleteByPlayerId(playerId);
        return HttpStatus.NO_CONTENT;
    }

    public TeamResponse savePlayer (TeamEntity teamEntity) {
        List<TeamEntity> listOfTeamEntity = new ArrayList<>();
        listOfTeamEntity.add(teamRepository.save(teamEntity));
        return new TeamResponse("201",listOfTeamEntity);
    }

    public TeamResponse updatePlayer(TeamEntity teamEntity) {
        TeamRequest teamRequest = new TeamRequest(teamEntity.playerId,teamEntity.playerName, teamEntity.playerPosition);
        validateTeamRequest(teamRequest);
        checkIfPlayerExists(teamRequest,"update");
        List<TeamEntity> listOfTeamEntity = new ArrayList<>();
        teamRepository.save(teamEntity);
        return new TeamResponse("200",listOfTeamEntity);
    }

    boolean validateTeamRequest(TeamRequest teamRequest) {
        List<ErrorDetails> errorDetailsList = new ArrayList<>();

        if(!validatePlayerId(teamRequest.getPlayerId())) {
            errorDetailsList.add(populateErrorDetails("Player Id","PlayerId cannot be empty and must be a number greater than zero"));
        }

        if(!validatePlayerName(teamRequest.getPlayerName())) {
            errorDetailsList.add(populateErrorDetails("Player Name", "Player Name cannot be empty or contain numbers and certain special characters"));
        }

        if(!validatePlayerPosition(teamRequest.getPlayerPosition())) {
            errorDetailsList.add(populateErrorDetails("Player Position", "You must select a player position."));
        }

        if (errorDetailsList.size() > 0) {
            ValidationError error = new ValidationError("Validationfailed", "There were validation errors", errorDetailsList);
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

    private boolean validatePlayerPosition(String playerPosition) {
        return !playerPosition.isEmpty() && playerPosition.matches(("^[a-zA-Z .'-]*$"));
    }

    void checkIfPlayerExists(TeamRequest teamRequest, String mode) throws ValidationException{
        Optional<TeamEntity> teamEntity = teamRepository.getOnePlayerRecordByPlayerId(teamRequest.getPlayerId());
        if (teamEntity.isPresent() && mode.equals("create")) {
            handleError(409);
        }
        else if (!teamEntity.isPresent() && mode.equals("update")) {
          handleError(404);
        }
    }

    private void handleError(int status) {
        List<ErrorDetails> errorDetailsList = new ArrayList<>();
            if(status == 409) {
            ErrorDetails expectedErrorDetails = new ErrorDetails("Conflict", "Player Id", "Player ID already exists.");
            errorDetailsList.add(expectedErrorDetails);
            ValidationError expectedValidationError = new ValidationError("Conflict", "Player ID already exists.", errorDetailsList);
            throw new ValidationException(409, expectedValidationError);
        }
            else {
            ErrorDetails expectedErrorDetails = new ErrorDetails("Not Found", "Player Id", "Player ID Not found.");
            errorDetailsList.add(expectedErrorDetails);
            ValidationError expectedValidationError = new ValidationError("Not Found", "Player ID Not found.", errorDetailsList);
            throw new ValidationException(404, expectedValidationError);
        }
    }
}
