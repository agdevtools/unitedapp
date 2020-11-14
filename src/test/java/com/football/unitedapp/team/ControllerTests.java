package com.football.unitedapp.team;


import com.football.unitedapp.repository.TeamEntity;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


public class ControllerTests {

    @Mock
    private TeamServiceImpl teamServiceImpl;

    @Mock
    private MeterRegistry meterRegistry;

    @InjectMocks
    private TeamController teamController;

    private MockMvc mockMvc;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setUpMockMvc() {
        mockMvc = standaloneSetup(new TeamController(teamServiceImpl))
        .build();
    }

    @Test
    public void test_whenGetPlayerById_thenreturnsPlayerEntity() {
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
    public void test_createPlayer_thenreturnsPlayerEntity() {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Test");
        TeamRequest teamRequest = new TeamRequest(7,"Test");
        when(teamServiceImpl.createPlayer(any(TeamEntity.class))).thenReturn(expectedTeamEntity);
        TeamEntity actualTeamEntity = teamController.createPlayer(teamRequest);
        assertEquals("Test", actualTeamEntity.getPlayerName());
        assertEquals(7, actualTeamEntity.getPlayerId());
    }

    @Test
    public void test_createPlayer_mvc_thenreturnsPlayerEntity() throws Exception {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Test");

        when(teamServiceImpl.createPlayer(any(TeamEntity.class)))
                .thenReturn(expectedTeamEntity);

        mockMvc.perform(post("/team")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"playerId\":\"7\",\"playerName\":\"Test\"}"))
        .andExpect(status().isCreated());


  //      TeamEntity actualTeamEntity = teamController.createPlayer(teamRequest);
//        assertEquals("Test", actualTeamEntity.getPlayerName());
//        assertEquals(7, actualTeamEntity.getPlayerId());
    }


    @Test
    public void test_deletePlayer_thenreturnsNullEntity() {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Test");
        TeamRequest teamRequest = new TeamRequest(7,"Test");
        when(teamServiceImpl.createPlayer(any())).thenReturn(expectedTeamEntity);
        TeamEntity actualteamEntity = teamController.createPlayer(teamRequest);
        assertEquals("Test", actualteamEntity.getPlayerName());
        assertEquals(7, actualteamEntity.getPlayerId());

        teamController.deletePlayer(actualteamEntity.playerId);
        TeamEntity deletedTeamEntity = teamServiceImpl.getPlayer(7);
        Assert.isNull(deletedTeamEntity);
    }
}
