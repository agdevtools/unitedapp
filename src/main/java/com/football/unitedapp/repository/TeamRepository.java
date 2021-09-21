package com.football.unitedapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {

    List<TeamEntity> findAll();
    TeamEntity findByPlayerId(int playerId);
    TeamEntity save(TeamEntity teamEntity);

    @Transactional
    HttpStatus deleteByPlayerId(int playerId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO team_stg (SELECT * from team)", nativeQuery = true)
    void copyTable();
}

