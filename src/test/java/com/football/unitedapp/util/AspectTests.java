package com.football.unitedapp.util;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.team.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
@Configuration
@Import({AspectConfig.class})
@SpringBootTest
public class AspectTests {

    @Autowired
    private TeamController teamController;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamServiceImpl teamServiceImpl;

    private static final Logger logger = Logger.getLogger(ControllerTests.class.getName());


    @Test
    @CaptureSystemOutput
    public void test_whenControllerGetTeam_thenAspectInterceptsandLogsOut(CaptureSystemOutput.OutputCapture capture) {
        teamController.getTeam();

        String consoleOutput = capture.toString();

        assertTrue(consoleOutput.contains("INFO"));
        assertTrue(consoleOutput.contains("com.football.unitedapp.team.TeamController.getTeam()"));
    }

    @Test
    @CaptureSystemOutput
    public void test_whenControllerGetPlayer_thenAspectInterceptsandLogsOut(CaptureSystemOutput.OutputCapture capture) {
        teamController.getPlayer(1);
        String consoleOutput = capture.toString();
        assertTrue(consoleOutput.contains("INFO"));
        assertTrue(consoleOutput.contains("com.football.unitedapp.team.TeamController.getPlayer"));
    }

    @Test
    public void test_whenUpdatePlayerThatDoesNotExist_thenReturn404Error() {
        try {
            teamServiceImpl.updatePlayer(new TeamEntity(7, "Paul Pogbab"));
        } catch (ValidationException ex) {
            assertEquals(404, ex.getStatus());
            assertEquals("Player ID Not found.",ex.getError().getMessage());
            assertEquals("Not Found",ex.getError().getCode());
        }
    }

    @Test
    public void test_whenCreatePlayerThatAlreadyExists_thenReturn409Error() {
        TeamRequest teamRequest = new TeamRequest(7,"Test Player");
        teamServiceImpl.createPlayer(teamRequest);

        try {
            teamServiceImpl.createPlayer(teamRequest);
        } catch (ValidationException ex) {
            assertEquals(409, ex.getStatus());
            assertEquals("Player ID already exists.",ex.getError().getMessage());
            assertEquals("Conflict",ex.getError().getCode());
        }
    }

    @Test
    public void test_whenUpdatePlayer_thenReturns200andEmptyTeamEntity() {
        TeamRequest teamRequest = new TeamRequest(6,"Test Player");
        teamServiceImpl.createPlayer(teamRequest);
        TeamEntity expectedTeamEntity = new TeamEntity(teamRequest.getPlayerId(), "Updated Player");

        TeamResponse actualTeamResponse = teamServiceImpl.updatePlayer(expectedTeamEntity);

        assertEquals("200",actualTeamResponse.getStatus());
        assertTrue(actualTeamResponse.getTeam().isEmpty());
    }

}
