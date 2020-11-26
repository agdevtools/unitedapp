package com.football.unitedapp;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.team.TeamRequest;
import com.football.unitedapp.team.TeamServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UnitedappApplicationTests {

	@Mock
	private TeamRepository teamRepository;

	@InjectMocks
	private TeamServiceImpl teamServiceImpl;

	@Captor
	private ArgumentCaptor<TeamEntity> captor;

	UnitedappApplication unitedappApplication;

	@Test
	public void test_whenCreatePlayer_thenWriteToRepository() {
		TeamRequest expectedTeamRequest = new TeamRequest(11, "Ryan Giggs");

		teamServiceImpl.createPlayer(expectedTeamRequest);
		Mockito.verify(teamRepository).save(captor.capture());
		TeamEntity actualPlayer = captor.getValue();
		Assertions.assertThat(actualPlayer.getPlayerName()).isEqualTo(expectedTeamRequest.getPlayerName());
		Assertions.assertThat(actualPlayer.getPlayerId()).isEqualTo(expectedTeamRequest.getPlayerId());
	}

	@Test
	public void test_getOperatingSystem() {

		String os = unitedappApplication.getOperatingSystem().toLowerCase();
		if (os.contains("mac")) {
			System.setProperty("spring.profiles.active", "local");
		} else {
			System.setProperty("spring.profiles.active", "prod");
		}

		assertEquals("local", System.getProperty("spring.profiles.active"));
	}

}

