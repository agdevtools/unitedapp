package com.football.unitedapp;


import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value="team")
public class TeamEntity {
    @PrimaryKeyColumn(value = "playerid", type = PrimaryKeyType.PARTITIONED, name = "playerid")
    public int playerId;
    @Column(value = "player_name")
    public String playerName;

    public TeamEntity(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }
}