package com.football.unitedapp.team;

public class TeamRequest {
    private int playerId;
    private String playerName;


    public TeamRequest(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
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

    public TeamRequest() {
        this.playerId = 0;
        this.playerName = "";
    }

}





