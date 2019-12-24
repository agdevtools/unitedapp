package com.football.unitedapp;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TeamServiceImpl teamServiceImpl(TeamRepository teamRepository) {
        return new TeamServiceImpl(teamRepository);
    }

    public TeamController teamController(TeamServiceImpl teamServiceImpl, MeterRegistry meter) {
        return new TeamController(teamServiceImpl, meter);
    }

}

