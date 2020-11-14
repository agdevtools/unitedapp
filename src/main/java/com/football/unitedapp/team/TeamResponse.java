package com.football.unitedapp.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class TeamResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    int playerId;

    public TeamResponse(HttpStatus status, int playerId, String playerName) {
        this.status = status;
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String playerName;
}
