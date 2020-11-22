package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
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
        TeamEntity expectedTeamEntity = new TeamEntity(6, "Paul Pogba");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponseTest expectedTeamResponse = new TeamResponseTest("200", expectedTeamEntityList);
        when(teamServiceImpl.getPlayer(anyInt())).thenReturn(expectedTeamResponse);

        TeamResponseTest actualTeamResponse = teamController.getPlayer(2);

        assertEquals("200", actualTeamResponse.status);
        assertEquals("Paul Pogba", actualTeamResponse.team.get(0).playerName);
        assertEquals(6, actualTeamResponse.team.get(0).playerId);
    }

    @Test
    public void test_whenGetTeam_thenreturnsListOfTeamEntity() {
        TeamEntity expectedTeamEntity1 = new TeamEntity(5, "Harry Maguire");
        TeamEntity expectedTeamEntity2 = new TeamEntity(6, "Paul Pogba");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity1);
        expectedTeamEntityList.add(expectedTeamEntity2);
        TeamResponseTest expectedTeamResponse = new TeamResponseTest("200", expectedTeamEntityList);
        when(teamServiceImpl.getTeam()).thenReturn(expectedTeamResponse);

        TeamResponseTest actualTeamResponse = teamController.getTeam();

        assertEquals("200", actualTeamResponse.status);
        assertEquals("Harry Maguire", actualTeamResponse.team.get(0).playerName);
        assertEquals(5, actualTeamResponse.team.get(0).playerId);
        assertEquals("Paul Pogba", actualTeamResponse.team.get(1).playerName);
        assertEquals(6, actualTeamResponse.team.get(1).playerId);
    }

    @Test
    public void test_whenCreatePlayer_thenreturnsCorrectTeamResponseBody() throws Exception {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Cantona");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponseTest expectedTeamResponse = new TeamResponseTest("201", expectedTeamEntityList);
        when(teamServiceImpl.createPlayer(any(TeamEntity.class)))
                .thenReturn(expectedTeamResponse);

         ResultActions resultActions = mockMvc.perform(post("/team")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"playerId\":\"7\",\"playerName\":\"Cantona\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.status", is("201")))
        .andExpect(jsonPath("$.team[0].playerName", is("Cantona")))
        .andExpect(jsonPath("$.team[0].playerId", is(7)));

    }

    @Test
    public void test_whenCreatePlayerNameWithNonAlphabeticCharacters_thenreturnsBadRequest()  {
        TeamRequest request = new TeamRequest(7,"%%%$$$");
        TeamEntity expectedTeamEntity = new TeamEntity(request.getPlayerId(), request.getPlayerName());
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponseTest expectedTeamResponse = new TeamResponseTest("404 Invalid request", expectedTeamEntityList);

        TeamResponseTest actualResponse = teamController.createPlayer(request);

        assertEquals(expectedTeamResponse.status, actualResponse.status);
    }

    @Test
    public void test_whenCreatePlayerContainingNumbers_thenreturnsBadRequest()  {
        TeamRequest request = new TeamRequest(7,"Player1");
        TeamEntity expectedTeamEntity = new TeamEntity(request.getPlayerId(), request.getPlayerName());
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponseTest expectedTeamResponse = new TeamResponseTest("404 Invalid request", expectedTeamEntityList);

        TeamResponseTest actualResponse = teamController.createPlayer(request);

        assertEquals(expectedTeamResponse.status, actualResponse.status);
    }

    @Test
    public void test_whenCreatePlayerWithEmptyPlayerName_thenreturnsBadRequest()  {
        TeamRequest request = new TeamRequest(7,"");
        TeamEntity expectedTeamEntity = new TeamEntity(request.getPlayerId(), request.getPlayerName());
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponseTest expectedTeamResponse = new TeamResponseTest("404 Invalid request", expectedTeamEntityList);

        TeamResponseTest actualResponse = teamController.createPlayer(request);

        assertEquals(expectedTeamResponse.status, actualResponse.status);
    }

    @Test
    public void test_whenValidateRequestPlayerNameContainsSpace_thenReturnsTrue()  {
        TeamRequest request = new TeamRequest(7,"Name WithSpace");
        teamController.validateTeamRequest(request);

        assertTrue(teamController.validateTeamRequest(request));
    }

    @Test
    public void test_whenValidateRequestPlayerNameContainsHypenAndinertedComma_thenReturnsTrue()  {
        TeamRequest request = new TeamRequest(7,"Name-With'InvertedComma andSpace");
        teamController.validateTeamRequest(request);

        assertTrue(teamController.validateTeamRequest(request));
    }

    @Test
    public void test_whenValidateRequestPlayerNameContainsSpecialCharacters_thenReturnsFalse()  {
        TeamRequest request = new TeamRequest(7,"Name WithSpecial%");
        teamController.validateTeamRequest(request);

        assertFalse(teamController.validateTeamRequest(request));
    }

    @Test
    public void test_whenCreatePlayerIdWithZero_thenreturnsBadRequest()  {
        TeamRequest request = new TeamRequest(0,"Player");
        TeamEntity expectedTeamEntity = new TeamEntity(request.getPlayerId(), request.getPlayerName());
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponseTest expectedTeamResponse = new TeamResponseTest("404 Invalid request", expectedTeamEntityList);

        TeamResponseTest actualResponse = teamController.createPlayer(request);

        assertEquals(expectedTeamResponse.status, actualResponse.status);
    }

    @Test
    public void test_whenUpdatePlayer_thenreturnsCorrectTeamResponseBody() throws Exception {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Cantona");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponseTest expectedTeamResponse = new TeamResponseTest("200", expectedTeamEntityList);
        when(teamServiceImpl.updatePlayer(any(TeamEntity.class)))
                .thenReturn(expectedTeamResponse);

        ResultActions resultActions = mockMvc.perform(put("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"playerId\":\"7\",\"playerName\":\"Cantona7\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("200")));
    }

    @Test
    public void test_whenUpdatePlayerWithInvalidBody_thenreturns400() throws Exception {

        ResultActions resultActions = mockMvc.perform(put("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void test_deletePlayer_thenreturnsNoContent() {
        TeamRequest teamRequest = new TeamRequest(7,"Test");
        HttpStatus expectedResponse = HttpStatus.NO_CONTENT;
        when(teamServiceImpl.deleteByPlayerId(anyInt())).thenReturn(HttpStatus.NO_CONTENT);
        HttpStatus actualResponse = teamController.deletePlayer(7);
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    public void test_whenDeletePlayer_thenreturnsCorrectResponseBody() throws Exception {
        HttpStatus expectedResponse = HttpStatus.NO_CONTENT;
        when(teamServiceImpl.deleteByPlayerId(anyInt())).thenReturn(expectedResponse);

        mockMvc.perform(delete("/team/{playerId}",7))
                .andExpect(status().isNoContent());
    }


}
