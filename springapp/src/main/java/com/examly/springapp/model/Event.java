package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;
    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String eventType;
    private String configuration;
    private boolean isActive;

    public Event() {}

    public Event(Long id, Long gameId, String eventName, LocalDate startDate,
                 LocalDate endDate, String eventType, String configuration, boolean isActive) {
        this.id = id;
        this.gameId = gameId;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
        this.configuration = configuration;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getConfiguration() { return configuration; }
    public void setConfiguration(String configuration) { this.configuration = configuration; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
