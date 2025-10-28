package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "snake_ladder_games")
public class SnakeAndLadderGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;
    private int currentPosition;
    private int moves;
    private String gameStatus; // playing, won, paused
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int finalScore;

    public SnakeAndLadderGame() {}

    public SnakeAndLadderGame(Long playerId, int currentPosition, int moves, String gameStatus, 
                             LocalDateTime startTime, LocalDateTime endTime, int finalScore) {
        this.playerId = playerId;
        this.currentPosition = currentPosition;
        this.moves = moves;
        this.gameStatus = gameStatus;
        this.startTime = startTime;
        this.endTime = endTime;
        this.finalScore = finalScore;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public int getCurrentPosition() { return currentPosition; }
    public void setCurrentPosition(int currentPosition) { this.currentPosition = currentPosition; }

    public int getMoves() { return moves; }
    public void setMoves(int moves) { this.moves = moves; }

    public String getGameStatus() { return gameStatus; }
    public void setGameStatus(String gameStatus) { this.gameStatus = gameStatus; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public int getFinalScore() { return finalScore; }
    public void setFinalScore(int finalScore) { this.finalScore = finalScore; }
}