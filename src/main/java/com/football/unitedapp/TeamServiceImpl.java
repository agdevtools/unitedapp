package com.football.unitedapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

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
