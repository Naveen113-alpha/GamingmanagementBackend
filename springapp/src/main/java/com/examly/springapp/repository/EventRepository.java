package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.Event;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByGameId(Long gameId);
    List<Event> findByIsActive(boolean isActive);
    List<Event> findByEventType(String eventType);
}
