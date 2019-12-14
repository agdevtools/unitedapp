package com.football.unitedapp;

import java.util.List;

public interface TeamService {

    List<TeamEntity> getTeam();

    TeamEntity getPlayer(int playerId);

    void createPlayer(int playerId, String playerName);
}
