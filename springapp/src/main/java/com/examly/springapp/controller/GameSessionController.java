package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.examly.springapp.model.GameSession;
import com.examly.springapp.service.GameSessionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gamesessions")
@CrossOrigin(origins = "*")
public class GameSessionController {

    @Autowired
    private GameSessionService gameSessionService;

    // Get all sessions
    @GetMapping
    public List<GameSession> getAllSessions() {
        return gameSessionService.getAllSessions();
    }

    // Get session by ID
    @GetMapping("/{id}")
    public Optional<GameSession> getSessionById(@PathVariable Long id) {
        return gameSessionService.getSessionById(id);
    }

    // Create new session
    @PostMapping
    public GameSession createSession(@RequestBody GameSession session) {
        return gameSessionService.createSession(session);
    }

    // Update existing session
    @PutMapping("/{id}")
    public Optional<GameSession> updateSession(@PathVariable Long id, @RequestBody GameSession session) {
        return gameSessionService.updateSession(id, session);
    }

    // Delete session
    @DeleteMapping("/{id}")
    public String deleteSession(@PathVariable Long id) {
        gameSessionService.deleteSession(id);
        return "GameSession with ID " + id + " deleted successfully.";
    }

    // Get all sessions for a player
    @GetMapping("/player/{playerId}")
    public List<GameSession> getSessionsByPlayerId(@PathVariable Long playerId) {
        return gameSessionService.getSessionsByPlayerId(playerId);
    }

    // Get all sessions for a game
    @GetMapping("/game/{gameId}")
    public List<GameSession> getSessionsByGameId(@PathVariable Long gameId) {
        return gameSessionService.getSessionsByGameId(gameId);
    }
}
