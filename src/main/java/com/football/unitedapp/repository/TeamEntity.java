package com.football.unitedapp.repository;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonPropertyOrder({ "player_id", "player_name" })
@Entity
@Table(name ="team")
public class TeamEntity {
    @Id
    @Column(name="player_id")
    @CsvBindByPosition(position = 0)
    public int playerId;
    @Column(name = "player_name")
    @CsvBindByPosition(position = 1)
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