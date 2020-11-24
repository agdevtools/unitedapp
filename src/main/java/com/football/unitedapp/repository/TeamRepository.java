package com.football.unitedapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {
    @Query(value = "SELECT * FROM team WHERE player_id = :playerId LIMIT 1;", nativeQuery = true)
    Optional<TeamEntity> getOnePlayerRecordByPlayerId(@Param("playerId") int playerId);

    List<TeamEntity> findAll();
    List<TeamEntity> findByPlayerId(int playerId);
    TeamEntity save(TeamEntity teamEntity);

    @Transactional
    HttpStatus deleteByPlayerId(int playerId);
}

