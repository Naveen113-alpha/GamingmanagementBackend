package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.GameSession;
import com.examly.springapp.repository.GameSessionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    // Get all sessions
    public List<GameSession> getAllSessions() {
        return gameSessionRepository.findAll();
    }

    // Get session by ID
    public Optional<GameSession> getSessionById(Long id) {
        return gameSessionRepository.findById(id);
    }

    // Create session
    public GameSession createSession(GameSession session) {
        return gameSessionRepository.save(session);
    }

    // Update session
    public Optional<GameSession> updateSession(Long id, GameSession updatedSession) {
        return gameSessionRepository.findById(id).map(session -> {
            session.setPlayerId(updatedSession.getPlayerId());
            session.setGameId(updatedSession.getGameId());
            session.setSessionStart(updatedSession.getSessionStart());
            session.setSessionEnd(updatedSession.getSessionEnd());
            session.setActionsPerformed(updatedSession.getActionsPerformed());
            session.setScore(updatedSession.getScore());
            return gameSessionRepository.save(session);
        });
    }

    // Delete session
    public void deleteSession(Long id) {
        gameSessionRepository.deleteById(id);
    }

    // Get sessions by player
    public List<GameSession> getSessionsByPlayerId(Long playerId) {
        return gameSessionRepository.findByPlayerId(playerId);
    }

    // Get sessions by game
    public List<GameSession> getSessionsByGameId(Long gameId) {
        return gameSessionRepository.findByGameId(gameId);
    }
}
