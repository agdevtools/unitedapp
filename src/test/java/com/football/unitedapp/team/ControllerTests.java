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
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        when(teamServiceImpl.getPlayer(anyInt())).thenReturn(expectedTeamEntity);
        TeamEntity actualteamEntity = teamController.getPlayer(2);
        assertEquals("Paul Pogba", actualteamEntity.getPlayerName());
        assertEquals(6, actualteamEntity.getPlayerId());
    }

    @Test
    public void test_whenGetTeam_thenreturnsListOfTeamEntity() {
        TeamEntity expectedTeamEntity1 = new TeamEntity(5, "Harry Maguire");
        TeamEntity expectedTeamEntity2 = new TeamEntity(6, "Paul Pogba");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<TeamEntity>();
        expectedTeamEntityList.add(expectedTeamEntity1);
        expectedTeamEntityList.add(expectedTeamEntity2);

        when(teamServiceImpl.getTeam()).thenReturn(expectedTeamEntityList);

        List<TeamEntity> actualTeamEntityList = teamController.getTeam();

        assertEquals(expectedTeamEntityList, actualTeamEntityList);
    }

    @Test
    public void test_whenCreatePlayer_thenreturnsCorrectTeamResponseBody() throws Exception {
        TeamResponse expectedTeamResponse = new TeamResponse(HttpStatus.CREATED,7,"Cantona");

        when(teamServiceImpl.createPlayer(any(TeamEntity.class)))
                .thenReturn(expectedTeamResponse);

         ResultActions resultActions = mockMvc.perform(post("/team")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"playerId\":\"7\",\"playerName\":\"Test\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.playerName", is("Cantona")))
        .andExpect(jsonPath("$.playerId", is(7)))
        .andExpect(jsonPath("$.status", is("CREATED")));
    }


    @Test
    public void test_deletePlayer_thenreturnsNullEntity() {
        TeamResponse expectedTeamResponse = new TeamResponse(HttpStatus.CREATED,7,"Test");
        TeamRequest teamRequest = new TeamRequest(7,"Test");
        when(teamServiceImpl.createPlayer(any())).thenReturn(expectedTeamResponse);
        TeamResponse actualTeamResponse = teamController.createPlayer(teamRequest);
        assertEquals("Test", actualTeamResponse.getPlayerName());
        assertEquals(7, actualTeamResponse.getPlayerId());

        teamController.deletePlayer(actualTeamResponse.getPlayerId());
        TeamEntity deletedTeamEntity = teamServiceImpl.getPlayer(7);
        Assert.isNull(deletedTeamEntity);
    }
}
