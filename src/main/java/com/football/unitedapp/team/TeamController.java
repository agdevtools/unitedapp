package com.football.unitedapp.team;

import com.football.unitedapp.dblogger.DBLoggingServiceImpl;
import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.util.AspectConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Import(AspectConfig.class)
@RestController
public class TeamController {

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    @Autowired
    private final DBLoggingServiceImpl dbLoggingService;

    final TeamService teamService;

    @Autowired
    public TeamController(TeamServiceImpl teamService, DBLoggingServiceImpl dbLoggingService) {
        this.teamService = teamService;
        this.dbLoggingService = dbLoggingService;
    }

    @GetMapping("/team")
    public TeamResponse getTeam()
    {
        dbLoggingService.logToDatabase(new Date(System.currentTimeMillis()),"/unitedapp/team","GET","");
        return teamService.getTeam();
    }

    @PostMapping("/team")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse createPlayer(@RequestBody TeamRequest teamRequest) {
        dbLoggingService.logToDatabase(new Date(System.currentTimeMillis()),"/unitedapp/team","POST",formatRequestForLogs(teamRequest));
        return teamService.createPlayer(teamRequest);
    }

    @PutMapping("/team")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponse updatePlayer(@RequestBody TeamRequest teamRequest) {
        dbLoggingService.logToDatabase(new Date(System.currentTimeMillis()),"/unitedapp/team","PUT",formatRequestForLogs(teamRequest));
        TeamEntity  teamEntity = new TeamEntity(teamRequest.getPlayerId(), teamRequest.getPlayerName(), teamRequest.getPlayerPosition());
        return teamService.updatePlayer(teamEntity);
    }

    @GetMapping("/team/{playerId}")
    public TeamResponse getPlayer(@PathVariable(value="playerId") Integer playerId)
    {
        dbLoggingService.logToDatabase(new Date(System.currentTimeMillis()),"/unitedapp/team{"+playerId+"}","GET","");
        return teamService.getPlayer(playerId);
    }

    @RequestMapping(value = "/team/league", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public String getLeagueTable() {
        dbLoggingService.logToDatabase(new Date(System.currentTimeMillis()),"/unitedapp/team/league","GET","");
        ResponseEntity<String> responseEntity =  teamService.getLeagueTable();
        return Objects.requireNonNull(responseEntity.getBody());
    }

    @DeleteMapping(value = "/team/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deletePlayer(@PathVariable(value="playerId") Integer playerId) {
        dbLoggingService.logToDatabase(new Date(System.currentTimeMillis()),"/unitedapp/team{"+playerId+"}","DELETE","");
        return teamService.deleteByPlayerId(playerId);
    }

    private String formatRequestForLogs(TeamRequest teamRequest) {
        return "{\n" +
                "  \"playerId\":" + teamRequest.getPlayerId() + ",\n" +
                " \"playerName\": \"" + teamRequest.getPlayerName() + "\",\n" +
                " \"playerPosition\": \"" + teamRequest.getPlayerPosition() +
                "\"\n}";
    }

}
