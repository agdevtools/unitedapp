package com.football.unitedapp.team;

import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

        TeamResponse actualTeamResponse = teamServiceImpl.getTeam();

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
        TeamResponse actualTeamResponse = teamServiceImpl.getPlayer(2);
        assertEquals("Paul Pogba", actualTeamResponse.team.get(0).playerName);
        assertEquals("200", actualTeamResponse.status);
    }

    @Test
    public void test_createPlayer_thenReturnsCorrectTeamResponse() {
        TeamEntity expectedTeamEntity = new TeamEntity(6, "Paul Pogba");
        when(teamRepository.save(any(TeamEntity.class))).thenReturn(expectedTeamEntity);

        TeamResponse actualTeamResponse = teamServiceImpl.createPlayer(new TeamRequest(6, "Paul Pogba"));

        assertEquals("Paul Pogba", actualTeamResponse.team.get(0).playerName);
        assertEquals("201", actualTeamResponse.status);
    }

    @Test
    public void test_whenValidateRequestPlayerNameContainsSpace_thenReturnsTrue()  {
        TeamRequest request = new TeamRequest(7,"Name WithSpace");
        teamServiceImpl.validateTeamRequest(request);

        assertTrue(teamServiceImpl.validateTeamRequest(request));
    }

    @Test
    public void test_whenValidateRequestPlayerNameContainsHypenAndinvertedComma_thenReturnsTrue()  {
        TeamRequest request = new TeamRequest(7,"Name-With'InvertedComma andSpace");
        teamServiceImpl.validateTeamRequest(request);

        assertTrue(teamServiceImpl.validateTeamRequest(request));
    }

    @Test
    public void test_whenValidateRequestPlayerNameContainsSpecialCharacters_thenThrowsValidationException()  {
        TeamRequest request = new TeamRequest(7,"Name WithSpecial%");

        assertThrows(ValidationException.class, () -> teamServiceImpl.validateTeamRequest(request));
    }

    @Test
    public void test_whenCreatePlayerNameWithNonAlphabeticCharacters_thenReturnsBadRequest()  {
        assertThrows(ValidationException.class, () -> teamServiceImpl.createPlayer(new TeamRequest(7,"%%%$$$")));
    }

    @Test
    public void test_whenCreatePlayerContainingNumbers_thenReturnsBadRequest() throws UnitedErrorHandler.BadRequestException {
        assertThrows(ValidationException.class, () -> teamServiceImpl.createPlayer(new TeamRequest(7,"Player1")));

    }

    @Test
    public void test_whenCreatePlayerWithEmptyPlayerName_thenreturnsBadRequest()  {
        assertThrows(ValidationException.class, () -> teamServiceImpl.createPlayer(new TeamRequest(7,"")));
    }

    @Test
    public void test_whenCreatePlayerIdWithZero_thenreturnsBadRequest()  {
        assertThrows(ValidationException.class, () -> teamServiceImpl.createPlayer(new TeamRequest(0,"Player")));
    }


    @Nested
    class TeamRequestTests {

        @Test
        public void test_givenInvalidPlayerId_thenReturnsErrorDetailswithCorrectResponse() {
            try {
                teamServiceImpl.validateTeamRequest(new TeamRequest(0,"Test"));
            } catch (ValidationException ex) {
                List<ErrorDetails> expectedErrorList = new ArrayList<>();
                expectedErrorList.addAll(ex.getError().getDetails());
                assertEquals(1,expectedErrorList.size());
                assertEquals("PlayerId must be a number greater than zero",expectedErrorList.get(0).getMessage());
                assertEquals("Player Id",expectedErrorList.get(0).getTarget());
            }
        }

        @Test
        public void test_givenInvalidPlayerName_thenReturnsErrorDetailswithCorrectResponse() {
            try {
                teamServiceImpl.validateTeamRequest(new TeamRequest(1,"££££££6776767%%%"));
            } catch (ValidationException ex) {
                List<ErrorDetails> expectedErrorList = new ArrayList<>();
                expectedErrorList.addAll(ex.getError().getDetails());
                assertEquals(1,expectedErrorList.size());
                assertEquals("Player Name cannot contain numbers and certain special characters",expectedErrorList.get(0).getMessage());
                assertEquals("Player Name",expectedErrorList.get(0).getTarget());
            }
        }

        @Test
        public void test_givenInvalidPlayerNameAndPlayerId_thenReturnsErrorDetailswithCorrectResponse() {
            try {
                teamServiceImpl.validateTeamRequest(new TeamRequest(0,"££££££6776767%%%"));
            } catch (ValidationException ex) {
                List<ErrorDetails> expectedErrorList = new ArrayList<>();
                expectedErrorList.addAll(ex.getError().getDetails());
                assertEquals(2,expectedErrorList.size());
                assertEquals("PlayerId must be a number greater than zero",expectedErrorList.get(0).getMessage());
                assertEquals("Player Id",expectedErrorList.get(0).getTarget());
                assertEquals("Player Name cannot contain numbers and certain special characters",expectedErrorList.get(1).getMessage());
                assertEquals("Player Name",expectedErrorList.get(1).getTarget());
            }
        }

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