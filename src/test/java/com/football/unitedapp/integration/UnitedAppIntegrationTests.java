package com.football.unitedapp.integration;

import com.football.unitedapp.UnitedappApplication;
import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.team.TeamController;
import com.football.unitedapp.team.TeamRequest;
import com.football.unitedapp.team.TeamResponse;
import org.aspectj.lang.annotation.Before;
import org.junit.Rule;
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
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ActiveProfiles("test")
@SpringBootTest(classes = {UnitedappApplication.class})
@WebAppConfiguration
@ContextConfiguration
public class UnitedAppIntegrationTests {

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private TeamRepository teamRepository;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void initMocks() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        prepareRepoForTest();
//    }
//
//    private void prepareRepoForTest() {
//        TeamRequest teamRequest = new TeamRequest();
//        teamRequest.setPlayerId(568);
//        teamRequest.setPlayerName("EmbedTest-req");
//        TeamEntity teamEntity = new TeamEntity(567,"EmbeddedTest","Test Pos");
//        teamRepository.save(teamEntity);
//    }
//
//    @Test
//    public void testEmbeddedDb() throws Exception {
//
//       mockMvc.perform(get("/team/567")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//               .andExpect(jsonPath("$.team[0].playerName", is("EmbeddedTest")));
//
//    }

    @Rule
    public PostgreSQLContainer postgresContainer = new PostgreSQLContainer();

    @Test
    public void whenSelectQueryExecuted_thenResulstsReturned()
            throws Exception {
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();
        Connection conn = DriverManager
                .getConnection(jdbcUrl, username, password);
        ResultSet resultSet =
                conn.createStatement().executeQuery("SELECT 1");
        resultSet.next();
        int result = resultSet.getInt(1);

        assertEquals(1, result);
    }

}
