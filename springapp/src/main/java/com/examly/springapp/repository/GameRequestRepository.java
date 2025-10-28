package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.GameRequest;
import java.util.List;

@Repository
public interface GameRequestRepository extends JpaRepository<GameRequest, Long> {
    List<GameRequest> findByUserId(Long userId);
    List<GameRequest> findByStatus(String status);
}