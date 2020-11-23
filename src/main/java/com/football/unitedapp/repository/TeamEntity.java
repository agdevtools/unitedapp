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

    public TeamEntity(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public TeamEntity() {
        this.playerId = 0;
        this.playerName = null;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }
}