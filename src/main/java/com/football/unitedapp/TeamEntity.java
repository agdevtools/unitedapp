package com.football.unitedapp;


import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value="team")
public class TeamEntity {
    private static final String insertQuery = "INSERT into team(" +
            "player_id, " +
            "player_name) " +
            "values(";

    @PrimaryKeyColumn(value = "player_id", type = PrimaryKeyType.PARTITIONED, name = "player_id")
    public int playerId;
    @Column(value = "player_name")
    public String playerName;

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

    public TeamEntity(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }


}