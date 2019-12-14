package com.football.unitedapp;

import java.util.List;

public interface TeamService {

    List<TeamEntity> getTeam();

    List<TeamEntity> getPlayer(int playerId);

    void createPlayer();
}
