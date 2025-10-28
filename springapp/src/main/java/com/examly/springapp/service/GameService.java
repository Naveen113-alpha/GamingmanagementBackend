package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.Game;
import com.examly.springapp.repository.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    // Get all games
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    // Get game by ID
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    // Create new game
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    // Update game
    public Optional<Game> updateGame(Long id, Game updatedGame) {
        return gameRepository.findById(id).map(game -> {
            game.setGameName(updatedGame.getGameName());
            game.setGameType(updatedGame.getGameType());
            game.setVersion(updatedGame.getVersion());
            game.setReleaseDate(updatedGame.getReleaseDate());
            game.setStatus(updatedGame.getStatus());
            game.setDeveloperId(updatedGame.getDeveloperId());
            game.setConfiguration(updatedGame.getConfiguration());
            return gameRepository.save(game);
        });
    }

    // Delete game
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
