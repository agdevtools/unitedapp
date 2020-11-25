package com.football.unitedapp.util;


import com.football.unitedapp.team.ControllerTests;
import com.football.unitedapp.team.TeamController;
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

    private static final Logger logger = Logger.getLogger(ControllerTests.class.getName());


    @Test
    @CaptureSystemOutput
    public void test_aspectWorks(CaptureSystemOutput.OutputCapture capture) {
        teamController.getTeam();

        String consoleOutput = capture.toString();

        assertTrue(consoleOutput.contains("INFO"));
        assertTrue(consoleOutput.contains("com.football.unitedapp.team.TeamController.getTeam()"));
    }

    @Test
    @CaptureSystemOutput
    public void test_aspectWorks_player(CaptureSystemOutput.OutputCapture capture) {
        teamController.getPlayer(1);
        String consoleOutput = capture.toString();
        assertTrue(consoleOutput.contains("INFO"));
        assertTrue(consoleOutput.contains("com.football.unitedapp.team.TeamController.getPlayer"));
    }

}
