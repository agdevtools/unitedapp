package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.util.UnitedErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
        TeamResponse expectedTeamResponse = new TeamResponse("200", expectedTeamEntityList);
        when(teamServiceImpl.getPlayer(anyInt())).thenReturn(expectedTeamResponse);

        TeamResponse actualTeamResponse = teamController.getPlayer(2);

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
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Cantona");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponse expectedTeamResponse = new TeamResponse("201", expectedTeamEntityList);
        when(teamServiceImpl.createPlayer(any(TeamRequest.class)))
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
    public void test_whenUpdatePlayer_thenreturnsCorrectTeamResponseBody() throws Exception {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Cantona");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        TeamResponse expectedTeamResponse = new TeamResponse("200", expectedTeamEntityList);
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
