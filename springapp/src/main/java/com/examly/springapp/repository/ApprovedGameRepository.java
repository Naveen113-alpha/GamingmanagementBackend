package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.ApprovedGame;
import java.util.List;

@Repository
public interface ApprovedGameRepository extends JpaRepository<ApprovedGame, Long> {
    List<ApprovedGame> findByUserId(Long userId);
}