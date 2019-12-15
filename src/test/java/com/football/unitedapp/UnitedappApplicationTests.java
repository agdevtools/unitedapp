package com.football.unitedapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitedappApplicationTests {

	@Captor
	private ArgumentCaptor<TeamEntity> teamEntityArgumentCaptor = ArgumentCaptor.forClass(TeamEntity.class);

	private TeamServiceImpl teamServiceImpl;

	@BeforeEach
	public void setUp() {
		teamServiceImpl = mock(TeamServiceImpl.class);
	}

	@Test
	void test_TeamServiceImpl_getTeamByPlayerId_returnsSingleTeamEntity() {

		TeamEntity expectedTeamEntity = new TeamEntity(5,"Harry Maguire");

		when(teamServiceImpl.getPlayer(anyInt())).thenReturn(expectedTeamEntity);

		assertEquals("Harry Maguire", expectedTeamEntity.getPlayerName());
		assertEquals(5, expectedTeamEntity.getPlayerId());
	}

	@Test
	void test_TeamServiceImpl_getTeam_returnsListofTeamEntity() {

		TeamEntity expectedTeamEntity1 = new TeamEntity(5, "Harry Maguire");
		TeamEntity expectedTeamEntity2 = new TeamEntity(6, "Paul Pogba");
		List<TeamEntity> expectedTeamEntityList = new ArrayList<TeamEntity>();
		expectedTeamEntityList.add(expectedTeamEntity1);
		expectedTeamEntityList.add(expectedTeamEntity2);

		when(teamServiceImpl.getTeam()).thenReturn(expectedTeamEntityList);

		List<TeamEntity> actualTeamEntityList = teamServiceImpl.getTeam();
		assertEquals(expectedTeamEntityList, actualTeamEntityList);
	}

	}
