package com.football.unitedapp;


import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value="team")
public class TeamEntity {
    private static final String insertQuery = "INSERT into team(" +
            "playerid, " +
            "player_name) " +
            "values(";


    @PrimaryKeyColumn(value = "playerid", type = PrimaryKeyType.PARTITIONED, name = "playerid")
    @Id
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