package com.football.unitedapp.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.football.unitedapp.repository.TeamEntity;

import java.util.List;

public class TeamResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<TeamEntity> team;

    public TeamResponse(String s, int playerId, String playerName) {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TeamEntity> getTeam() {
        return team;
    }

    public void setTeam(List<TeamEntity> team) {
        this.team = team;
    }

    public TeamResponse(String status, List<TeamEntity> team) {
        this.status = status;
        this.team = team;
    }
}
