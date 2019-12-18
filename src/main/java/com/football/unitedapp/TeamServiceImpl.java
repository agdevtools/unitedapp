package com.football.unitedapp;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<TeamEntity> getTeam() {
        return teamRepository.findAll();
    }

    @Override
    public TeamEntity getPlayer(int playerId) {
        return teamRepository.findByPlayerId(playerId);
    }

    @Override
    public TeamEntity createPlayer(TeamEntity teamEntity) {
      teamRepository.save(teamEntity);
      return teamEntity;
    }

    public TeamEntity createPlayer() {
            TeamEntity teamEntity = new TeamEntity(3,"Luke Shaw");
            return teamRepository.save(teamEntity);
        }
}
