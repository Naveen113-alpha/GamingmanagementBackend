package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "game_requests")
public class GameRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long gameId;
    private String gameName;
    private String gameIcon;
    private String playerName;
    private String status; // pending, approved, rejected
    private LocalDate requestDate;

    public GameRequest() {}

    public GameRequest(Long userId, Long gameId, String gameName, String gameIcon, 
                      String playerName, String status, LocalDate requestDate) {
        this.userId = userId;
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameIcon = gameIcon;
        this.playerName = playerName;
        this.status = status;
        this.requestDate = requestDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public String getGameIcon() { return gameIcon; }
    public void setGameIcon(String gameIcon) { this.gameIcon = gameIcon; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }
}