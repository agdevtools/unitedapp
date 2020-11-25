package com.football.unitedapp.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.football.unitedapp.repository.TeamEntity;

import java.util.List;

public class TeamResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<TeamEntity> team;


    public String getStatus() {
        return status;
    }

    public List<TeamEntity> getTeam() {
        return team;
    }

    public TeamResponse(String status, List<TeamEntity> team) {
        this.status = status;
        this.team = team;
    }
}
