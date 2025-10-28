package com.examly.springapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tournament_registrations")
public class TournamentRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;
    
    @Column(name = "tournament_name", nullable = false)
    private String tournamentName;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "player_name", nullable = false)
    private String playerName;
    
    @Column(name = "player_email", nullable = false)
    private String playerEmail;
    
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;
    
    @Column(name = "status", nullable = false)
    private String status = "pending";
    
    @Column(name = "game_name")
    private String gameName;
    
    @Column(name = "prize_pool")
    private String prizePool;

    public TournamentRegistration() {}

    public TournamentRegistration(Long tournamentId, String tournamentName, Long userId, String playerName, String playerEmail, String gameName, String prizePool) {
        this.tournamentId = tournamentId;
        this.tournamentName = tournamentName;
        this.userId = userId;
        this.playerName = playerName;
        this.playerEmail = playerEmail;
        this.gameName = gameName;
        this.prizePool = prizePool;
        this.registrationDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTournamentId() { return tournamentId; }
    public void setTournamentId(Long tournamentId) { this.tournamentId = tournamentId; }

    public String getTournamentName() { return tournamentName; }
    public void setTournamentName(String tournamentName) { this.tournamentName = tournamentName; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getPlayerEmail() { return playerEmail; }
    public void setPlayerEmail(String playerEmail) { this.playerEmail = playerEmail; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public String getPrizePool() { return prizePool; }
    public void setPrizePool(String prizePool) { this.prizePool = prizePool; }
}