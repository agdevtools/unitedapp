package com.football.unitedapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void test_serviceImplWhenGetTeam_thenreturnsListOfTeamEntity() {
        TeamEntity expectedTeamEntity1 = new TeamEntity(5, "Harry Maguire");
        TeamEntity expectedTeamEntity2 = new TeamEntity(6, "Paul Pogba");
        List<TeamEntity> expectedTeamEntityList = new ArrayList<TeamEntity>();
        expectedTeamEntityList.add(expectedTeamEntity1);
        expectedTeamEntityList.add(expectedTeamEntity2);

        when(teamRepository.findAll()).thenReturn(expectedTeamEntityList);

        List<TeamEntity> actualTeamEntityList = teamServiceImpl.getTeam();

        assertEquals(expectedTeamEntityList, actualTeamEntityList);
    }

    @Test
    public void test_serviceImplWhenGetPlayerById_thenreturnsPlayerEntity() {
        TeamEntity expectedTeamEntity = new TeamEntity(6, "Paul Pogba");
        when(teamRepository.findByPlayerId(anyInt())).thenReturn(expectedTeamEntity);
        TeamEntity actualteamEntity = teamServiceImpl.getPlayer(2);
        assertEquals("Paul Pogba", actualteamEntity.getPlayerName());
        assertEquals(6, actualteamEntity.getPlayerId());
    }

    @Test
    public void test_createPlayer_thenreturnsPlayerEntity() {
        TeamEntity expectedTeamEntity = new TeamEntity(7, "Test");
        when(teamRepository.save(expectedTeamEntity)).thenReturn(expectedTeamEntity);
        TeamEntity actualteamEntity = teamServiceImpl.createPlayer(expectedTeamEntity);
        assertEquals("Test", actualteamEntity.getPlayerName());
        assertEquals(7, actualteamEntity.getPlayerId());
    }
}