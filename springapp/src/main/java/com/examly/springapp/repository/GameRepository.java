package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // You can add custom queries here if needed
}
