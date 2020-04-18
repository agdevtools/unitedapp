package com.football.unitedapp.team;


import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.team.TeamController;
import com.football.unitedapp.team.TeamServiceImpl;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;



public class ControllerTests {

    @Mock
    private TeamServiceImpl teamServiceImpl;

    @Mock
    private MeterRegistry meterRegistry;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
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
    public void test_whenGeTeam_thenreturnsListOfTeamEntity() {
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
        when(teamServiceImpl.createPlayer(expectedTeamEntity)).thenReturn(expectedTeamEntity);
        TeamEntity actualteamEntity = teamController.createPlayer("Test",7);
        assertEquals("Test", actualteamEntity.getPlayerName());
        assertEquals(7, actualteamEntity.getPlayerId());
    }

    @Test
    public void test_deletePlayer_thenreturnsNullEntity() {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Test");
        when(teamServiceImpl.createPlayer(any())).thenReturn(expectedTeamEntity);
        TeamEntity actualteamEntity = teamController.createPlayer("Test",7);
        assertEquals("Test", actualteamEntity.getPlayerName());
        assertEquals(7, actualteamEntity.getPlayerId());

        teamController.deletePlayer(actualteamEntity.playerId);
        TeamEntity deletedTeamEntity = teamServiceImpl.getPlayer(7);
        Assert.isNull(deletedTeamEntity);
    }
}
