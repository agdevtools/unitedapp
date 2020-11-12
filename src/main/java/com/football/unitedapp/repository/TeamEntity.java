package com.football.unitedapp.repository;

import javax.persistence.*;


@Entity
@Table(name ="team")
public class TeamEntity {
    @Id
    @Column(name="player_id")
    public int playerId;
    @Column(name = "player_name")
    public String playerName;

    public TeamEntity(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public TeamEntity() {
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }
}