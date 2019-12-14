package com.football.unitedapp;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {

    List<TeamEntity> getTeam();

    List<TeamEntity> getPlayer(int playerId);

    void createPlayer();
}
