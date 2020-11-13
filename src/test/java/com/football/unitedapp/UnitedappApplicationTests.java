package com.football.unitedapp;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.team.TeamServiceImpl;
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
		TeamEntity expectedPlayer = TeamEntity.builder()
				.playerId(11)
				.playerName("Ryan Giggs")
				.build();

		teamService.createPlayer(expectedPlayer);
		Mockito.verify(teamRepository).save(captor.capture());
		TeamEntity actualPlayer = captor.getValue();
		Assertions.assertThat(actualPlayer.getPlayerName()).isEqualTo(expectedPlayer.getPlayerName());
		Assertions.assertThat(actualPlayer.getPlayerId()).isEqualTo(expectedPlayer.getPlayerId());
	}
}
