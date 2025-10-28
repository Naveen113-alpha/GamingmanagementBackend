package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long gameId;
    private String playerName;
    private int level;
    private double experience;
    private double currency;
    private LocalDate lastPlayDate;
    private double totalPlayTime; // in hours
    private boolean isActive;

    public Player() {}

    public Player(Long id, Long userId, Long gameId, String playerName, int level, double experience,
                  double currency, LocalDate lastPlayDate, double totalPlayTime, boolean isActive) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
        this.playerName = playerName;
        this.level = level;
        this.experience = experience;
        this.currency = currency;
        this.lastPlayDate = lastPlayDate;
        this.totalPlayTime = totalPlayTime;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public double getExperience() { return experience; }
    public void setExperience(double experience) { this.experience = experience; }

    public double getCurrency() { return currency; }
    public void setCurrency(double currency) { this.currency = currency; }

    public LocalDate getLastPlayDate() { return lastPlayDate; }
    public void setLastPlayDate(LocalDate lastPlayDate) { this.lastPlayDate = lastPlayDate; }

    public double getTotalPlayTime() { return totalPlayTime; }
    public void setTotalPlayTime(double totalPlayTime) { this.totalPlayTime = totalPlayTime; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
