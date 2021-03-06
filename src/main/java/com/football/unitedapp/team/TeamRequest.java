package com.football.unitedapp.team;

public class TeamRequest {
    private int playerId;
    private String playerName;
    private String playerPosition;


    public TeamRequest(int playerId, String playerName, String playerPosition) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerPosition = playerPosition;
    }

    @Override
    public String toString() {
        return "TeamRequest{" +
                "\"playerId\":" + playerId +
                ", \"playerName\": " + "\"" + playerName + "\" "  +
                ", \"playerPosition\": " +  "\"" + playerPosition + "\" "  +
                '}';
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

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public TeamRequest() {
        this.playerId = 0;
        this.playerName = "";
        this.playerPosition = "";
    }

}





