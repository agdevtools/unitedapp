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
    public void test_serviceImplWhenGetTeam_thenreturnsCorrectResponseBody() {
        TeamEntity expectedTeamEntity1 = new TeamEntity(5, "Harry Maguire");
        TeamEntity expectedTeamEntity2 = new TeamEntity(6, "Paul Pogba");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<TeamEntity>();
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

//    @Test
//    public void test_serviceImplWhenGetPlayerById_thenreturnsPlayerEntity() {
//        TeamEntity expectedTeamEntity = new TeamEntity(6, "Paul Pogba");
//        when(teamRepository.findByPlayerId(anyInt())).thenReturn(expectedTeamEntity);
//        TeamResponseTest actualTeamResponse = teamServiceImpl.getPlayer(2);
//        assertEquals("Paul Pogba", actualTeamResponse.getTeam());
//        assertEquals("200", actualTeamResponse.getStatus());
//    }

//    @Test
//    public void test_createPlayer_thenreturnsTeamResponse() {
//        TeamResponse expectedTeamResponse = new TeamResponse(HttpStatus.CREATED,6,"Paul Pogba");
//
//        TeamEntity teamEntity = new TeamEntity(6, "Paul Pogba");
//        when(teamRepository.save(any(TeamEntity.class))).thenReturn(teamEntity);
//
//        TeamResponse actualTeamResponse = teamServiceImpl.createPlayer(teamEntity);
//
//        assertEquals(expectedTeamResponse.getPlayerName(), actualTeamResponse.getPlayerName());
//        assertEquals(expectedTeamResponse.getPlayerId(), actualTeamResponse.getPlayerId());
//    }

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
//        TeamEntity deletedTeamEntity = teamServiceImpl.getPlayer(7);
//        Assert.isNull(deletedTeamEntity);
//        }
}