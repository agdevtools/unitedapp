package com.football.unitedapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UnitedappApplicationTests {


	@Mock
	private TeamRepository teamRepository;


	@InjectMocks
	private TeamServiceImpl teamService;

	@Captor
	private ArgumentCaptor<TeamEntity> captor;

	@Test
	public void test_whenCreatePlayer_thenWriteToRepository() {
		TeamEntity expectedPlayer = new TeamEntity(11,"Ryan Giggs");
		teamService.createPlayer(expectedPlayer);
		Mockito.verify(teamRepository).save(captor.capture());
		TeamEntity actualPlayer = captor.getValue();
		Assertions.assertThat(actualPlayer.getPlayerName()).isEqualTo(expectedPlayer.getPlayerName());
		Assertions.assertThat(actualPlayer.getPlayerId()).isEqualTo(expectedPlayer.getPlayerId());
	}



}
