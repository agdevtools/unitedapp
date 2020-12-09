package com.football.unitedapp.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="team")
public class TeamEntity {
    @Id
    @Column(name="player_id", unique = true)
    public int playerId;
    @Column(name = "player_name")
    public String playerName;
    @Column(name = "player_position")
    public String playerPosition;

    public TeamEntity(int playerId, String playerName, String playerPosition) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerPosition = playerPosition;
    }

    public TeamEntity() {
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }
}