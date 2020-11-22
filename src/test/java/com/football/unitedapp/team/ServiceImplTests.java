package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ServiceImplTests {
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamServiceImpl;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_serviceImplWhenGetTeam_thenReturnsCorrectResponseBody() {
        TeamEntity expectedTeamEntity1 = new TeamEntity(5, "Harry Maguire");
        TeamEntity expectedTeamEntity2 = new TeamEntity(6, "Paul Pogba");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity1);
        expectedTeamEntityList.add(expectedTeamEntity2);

        when(teamRepository.findAll()).thenReturn(expectedTeamEntityList);

        TeamResponseTest actualTeamResponse = teamServiceImpl.getTeam();

        assertEquals("200", actualTeamResponse.status);
        assertEquals(5,actualTeamResponse.team.get(0).playerId);
        assertEquals(6,actualTeamResponse.team.get(1).playerId);
        assertEquals("Harry Maguire",actualTeamResponse.team.get(0).playerName);
        assertEquals("Paul Pogba",actualTeamResponse.team.get(1).playerName);
    }

    @Test
    public void test_serviceImplWhenGetPlayerById_thenReturnsPlayerEntityInCorrectResponseBody() {
        TeamEntity expectedTeamEntity = new TeamEntity(6, "Paul Pogba");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<>();
        expectedTeamEntityList.add(expectedTeamEntity);
        when(teamRepository.findByPlayerId(anyInt())).thenReturn(expectedTeamEntityList);
        TeamResponseTest actualTeamResponse = teamServiceImpl.getPlayer(2);
        assertEquals("Paul Pogba", actualTeamResponse.team.get(0).playerName);
        assertEquals("200", actualTeamResponse.status);
    }

    @Test
    public void test_createPlayer_thenReturnsCorrectTeamResponse() {
        TeamEntity expectedTeamEntity = new TeamEntity(6, "Paul Pogba");
        when(teamRepository.save(any(TeamEntity.class))).thenReturn(expectedTeamEntity);

        TeamResponseTest actualTeamResponse = teamServiceImpl.createPlayer(new TeamEntity(6, "Paul Pogba"));

        assertEquals("Paul Pogba", actualTeamResponse.team.get(0).playerName);
        assertEquals("201", actualTeamResponse.status);
    }

//    @Test
//    public void test_deletePlayer_thenpPlayerNoLongerExists() {
//        TeamEntity expectedTeamEntity = new TeamEntity(7, "Test");
//        teamServiceImpl.createPlayer(expectedTeamEntity);
//
//        assertEquals("Test", expectedTeamEntity.getPlayerName());
//        assertEquals(7, expectedTeamEntity.getPlayerId());
//
//        teamServiceImpl.deleteByPlayerId(expectedTeamEntity.playerId);
//
//        TeamResponseTest actualTeamResponse = teamServiceImpl.getPlayer(7);
//        assertEquals("400", actualTeamResponse.status);
//        }
}