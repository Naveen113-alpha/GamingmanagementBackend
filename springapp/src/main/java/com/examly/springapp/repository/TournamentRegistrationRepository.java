package com.examly.springapp.repository;

import com.examly.springapp.model.TournamentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TournamentRegistrationRepository extends JpaRepository<TournamentRegistration, Long> {
    List<TournamentRegistration> findByUserId(Long userId);
    List<TournamentRegistration> findByStatus(String status);
    boolean existsByUserIdAndTournamentId(Long userId, Long tournamentId);
}