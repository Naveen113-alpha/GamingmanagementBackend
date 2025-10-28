package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_sessions")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;
    private Long gameId;
    private LocalDateTime sessionStart;
    private LocalDateTime sessionEnd;
    private int actionsPerformed;
    private double score;

    public GameSession() {}

    public GameSession(Long id, Long playerId, Long gameId, LocalDateTime sessionStart,
                       LocalDateTime sessionEnd, int actionsPerformed, double score) {
        this.id = id;
        this.playerId = playerId;
        this.gameId = gameId;
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
        this.actionsPerformed = actionsPerformed;
        this.score = score;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public LocalDateTime getSessionStart() { return sessionStart; }
    public void setSessionStart(LocalDateTime sessionStart) { this.sessionStart = sessionStart; }

    public LocalDateTime getSessionEnd() { return sessionEnd; }
    public void setSessionEnd(LocalDateTime sessionEnd) { this.sessionEnd = sessionEnd; }

    public int getActionsPerformed() { return actionsPerformed; }
    public void setActionsPerformed(int actionsPerformed) { this.actionsPerformed = actionsPerformed; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
}
