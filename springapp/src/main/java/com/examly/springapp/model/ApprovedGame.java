package com.examly.springapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "approved_games")
public class ApprovedGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long gameId;
    private String gameName;
    private String gameIcon;
    private String difficulty;
    private String route;

    public ApprovedGame() {}

    public ApprovedGame(Long userId, Long gameId, String gameName, String gameIcon, String difficulty, String route) {
        this.userId = userId;
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameIcon = gameIcon;
        this.difficulty = difficulty;
        this.route = route;
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

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
}