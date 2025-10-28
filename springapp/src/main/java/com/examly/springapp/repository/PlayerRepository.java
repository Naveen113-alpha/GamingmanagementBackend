package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.Player;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByUserId(Long userId);
    List<Player> findByGameId(Long gameId);
    Optional<Player> findByUserIdAndGameId(Long userId, Long gameId);
}
