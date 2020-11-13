package com.football.unitedapp.repository;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="team")
public class TeamEntity {
    @Id
    @Column(name="player_id")
    public int playerId;
    @Column(name = "player_name")
    public String playerName;
}