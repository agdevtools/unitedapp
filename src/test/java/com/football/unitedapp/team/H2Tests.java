package com.football.unitedapp.team;

import com.football.unitedapp.UnitedappApplication;
import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(classes = {UnitedappApplication.class})
@WebAppConfiguration
@ContextConfiguration
public class H2Tests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TeamRepository teamRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        prepareRepoForTest();
    }

    private void prepareRepoForTest() {
        TeamRequest teamRequest = new TeamRequest();
        teamRequest.setPlayerId(568);
        teamRequest.setPlayerName("EmbedTest-req");
        TeamEntity teamEntity = new TeamEntity(567,"EmbeddedTest","Test Pos");
        teamRepository.save(teamEntity);
    }

    @Test
    public void testEmbeddedDb() throws Exception {

        mockMvc.perform(get("/team/567")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team[0].playerName", is("EmbeddedTest")));

    }
}
