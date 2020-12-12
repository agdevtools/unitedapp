package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.util.ErrorDetails;
import com.football.unitedapp.util.ValidationError;
import com.football.unitedapp.util.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


public class ControllerTests {

    @Mock
    private TeamServiceImpl teamServiceImpl;

    @InjectMocks
    private TeamController teamController;

    private MockMvc mockMvc;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(new TeamController(teamServiceImpl))
                .build();
    }

    @Test
    public void test_whenGetPlayerById_thenreturnsCorrectTeamResponseBody() {
        TeamEntity expectedTeamEntity = new TeamEntity(6, "Paul Pogba", "Midfielder");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponse expectedTeamResponse = new TeamResponse("200", expectedTeamEntityList);
        when(teamServiceImpl.getPlayer(anyInt())).thenReturn(expectedTeamResponse);

        TeamResponse actualTeamResponse = teamController.getPlayer(2);

        assertEquals("200", actualTeamResponse.status);
        assertEquals("Paul Pogba", actualTeamResponse.team.get(0).playerName);
        assertEquals(6, actualTeamResponse.team.get(0).playerId);
    }

    @Test
    public void test_whenGetTeam_thenreturnsListOfTeamEntity() {
        TeamEntity expectedTeamEntity1 = new TeamEntity(5, "Harry Maguire","Defender");
        TeamEntity expectedTeamEntity2 = new TeamEntity(6, "Paul Pogba", "Midfielder");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity1);
        expectedTeamEntityList.add(expectedTeamEntity2);
        TeamResponse expectedTeamResponse = new TeamResponse("200", expectedTeamEntityList);
        when(teamServiceImpl.getTeam()).thenReturn(expectedTeamResponse);

        TeamResponse actualTeamResponse = teamController.getTeam();

        assertEquals("200", actualTeamResponse.status);
        assertEquals("Harry Maguire", actualTeamResponse.team.get(0).playerName);
        assertEquals(5, actualTeamResponse.team.get(0).playerId);
        assertEquals("Paul Pogba", actualTeamResponse.team.get(1).playerName);
        assertEquals(6, actualTeamResponse.team.get(1).playerId);
    }


    @Test
    public void test_whenCreatePlayer_thenreturnsCorrectTeamResponseBody() throws Exception {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Cantona","Midfielder");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponse expectedTeamResponse = new TeamResponse("201", expectedTeamEntityList);
        when(teamServiceImpl.createPlayer(any(TeamRequest.class)))
                .thenReturn(expectedTeamResponse);

         mockMvc.perform(post("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"playerId\":\"7\",\"playerName\":\"Cantona\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("201")))
                .andExpect(jsonPath("$.team[0].playerName", is("Cantona")))
                .andExpect(jsonPath("$.team[0].playerId", is(7)));

    }


    @Test
    public void test_whenUpdatePlayer_thenreturnsCorrectTeamResponseBody() throws Exception {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Cantona","Midfielder");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponse expectedTeamResponse = new TeamResponse("200", expectedTeamEntityList);
        when(teamServiceImpl.updatePlayer(any(TeamEntity.class)))
                .thenReturn(expectedTeamResponse);

        mockMvc.perform(put("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"playerId\":\"7\",\"playerName\":\"Cantona7\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("200")));
    }

    @Test
    public void test_whengetLeague_thenreturnsCorrectResponse() {
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Football",HttpStatus.OK);
        when(teamServiceImpl.getLeagueTable()).thenReturn(expectedResponse);

        String actualTeamResponse = teamController.getLeagueTable();

        assertEquals("Football", actualTeamResponse);
    }

    @Test
    public void test_whenUpdatePlayerWithInvalidBody_thenreturns400() throws Exception {

         mockMvc.perform(put("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void test_deletePlayer_thenreturnsNoContent() {
        HttpStatus expectedResponse = HttpStatus.NO_CONTENT;
        when(teamServiceImpl.deleteByPlayerId(anyInt())).thenReturn(HttpStatus.NO_CONTENT);
        HttpStatus actualResponse = teamController.deletePlayer(7);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void test_whenDeletePlayer_thenreturnsCorrectResponseBody() throws Exception {
        HttpStatus expectedResponse = HttpStatus.NO_CONTENT;
        when(teamServiceImpl.deleteByPlayerId(anyInt())).thenReturn(expectedResponse);

        mockMvc.perform(delete("/team/{playerId}", 7))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_whenCreatePlayerIdWithZero_thenreturnsValidationExceptionError() {
        List<ErrorDetails> expectedErrorList = new ArrayList<>();
        ErrorDetails expectedErrorDetails = new ErrorDetails("404", "Player Id", "PlayerId must be a number greater than zero");
        expectedErrorList.add(expectedErrorDetails);
        ValidationError expectedValidationError = new ValidationError("test code", "test message", expectedErrorList);
        ValidationException expectedValidationException = new ValidationException(404, expectedValidationError);
        when(teamServiceImpl.createPlayer(any(TeamRequest.class))).thenThrow(expectedValidationException);

        try {
            teamServiceImpl.createPlayer(new TeamRequest(1, "Test","Test"));
        } catch (ValidationException ex) {
            assertEquals(404, ex.getStatus());
            assertEquals("test code", ex.getError().getCode());
            assertEquals("test message", ex.getError().getMessage());
            assertEquals("404", ex.getError().getDetails().get(0).getCode());
            assertEquals("Player Id", ex.getError().getDetails().get(0).getTarget());
            assertEquals("PlayerId must be a number greater than zero", ex.getError().getDetails().get(0).getMessage());
        }
    }

    @Test
    public void test_whenCreatePlayerIdAlresdy_thenreturnsValidationExceptionError() {
        List<ErrorDetails> expectedErrorList = new ArrayList<>();
        ErrorDetails expectedErrorDetails = new ErrorDetails("Conflict", "PlayerId", "Player ID already exists.");
        expectedErrorList.add(expectedErrorDetails);
        ValidationError expectedValidationError = new ValidationError("Conflict", "Player ID already exists.", expectedErrorList);
        ValidationException expectedValidationException = new ValidationException(409, expectedValidationError);
        when(teamServiceImpl.createPlayer(any(TeamRequest.class))).thenThrow(expectedValidationException);

        try {
            teamServiceImpl.createPlayer(new TeamRequest(1, "Test","Test"));
        } catch(ValidationException ex) {
            assertEquals(409, ex.getStatus());
            assertEquals("Conflict", ex.getError().getCode());
            assertEquals("Player ID already exists.", ex.getError().getMessage());
            assertEquals("PlayerId", ex.getError().getDetails().get(0).getTarget());
        }
    }
}
