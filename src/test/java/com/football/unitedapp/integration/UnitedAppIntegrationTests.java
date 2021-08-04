package com.football.unitedapp.integration;

import com.football.unitedapp.UnitedappApplication;
import com.football.unitedapp.repository.TeamEntity;
import com.football.unitedapp.repository.TeamRepository;
import com.football.unitedapp.team.TeamRequest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles("test")
//@SpringBootTest(classes = {UnitedappApplication.class})
//@WebAppConfiguration
//@ContextConfiguration
//public class UnitedAppIntegrationTests {

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

    @RunWith(SpringRunner.class)
    @SpringBootTest
    @ContextConfiguration(initializers = {UnitedAppIntegrationTests.Initializer.class})
    public class UnitedAppIntegrationTests  {

        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        private TeamRepository teamRepository;

        private MockMvc mockMvc;

        @ClassRule
        public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");

        static class Initializer
                implements ApplicationContextInitializer<ConfigurableApplicationContext> {
            public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
                TestPropertyValues.of(
                        "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                        "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                        "spring.datasource.password=" + postgreSQLContainer.getPassword()
                ).applyTo(configurableApplicationContext.getEnvironment());
            }
        }

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
        public void testmytest() {
            assertEquals(1,1);
        }



//        @Test
//        public void testEmbeddedDb() throws Exception {
//
//            mockMvc.perform(get("/team/567")
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.team[0].playerName", is("EmbeddedTest")));
//
//        }

//        @Test
//        @Transactional
//        public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationJPQL_ThenModifyMatchingUsers(){
//            insertUsers();
//            int updatedUsersSize = userRepository.updateUserSetStatusForName(0, "SAMPLE");
//            assertThat(updatedUsersSize).isEqualTo(2);
//        }
//
//        @Test
//        @Transactional
//        public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationNative_ThenModifyMatchingUsers(){
//            insertUsers();
//            int updatedUsersSize = userRepository.updateUserSetStatusForNameNative(0, "SAMPLE");
//            assertThat(updatedUsersSize).isEqualTo(2);
//        }
//
//        private void insertUsers() {
//            userRepository.save(new User("SAMPLE", "email@example.com", 1));
//            userRepository.save(new User("SAMPLE1", "email2@example.com", 1));
//            userRepository.save(new User("SAMPLE", "email3@example.com", 1));
//            userRepository.save(new User("SAMPLE3", "email4@example.com", 1));
//            userRepository.flush();
//        }
    }

