package com.football.unitedapp.integration;

import com.football.unitedapp.UnitedappApplication;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
