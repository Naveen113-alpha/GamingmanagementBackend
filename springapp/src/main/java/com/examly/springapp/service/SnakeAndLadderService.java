package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.SnakeAndLadderGame;
import com.examly.springapp.model.Player;
import com.examly.springapp.repository.PlayerRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SnakeAndLadderService {

    @Autowired
    private PlayerRepository playerRepository;

    public void updatePlayerProgress(Long userId, String playerName, int moves, boolean gameWon) {
        // Find or create player for Snake and Ladder game (gameId = 1)
        Optional<Player> existingPlayer = playerRepository.findByUserIdAndGameId(userId, 1L);
        
        Player player;
        if (existingPlayer.isPresent()) {
            player = existingPlayer.get();
        } else {
            player = new Player();
            player.setUserId(userId);
            player.setGameId(1L); // Snake and Ladder game ID
            player.setPlayerName(playerName);
            player.setLevel(1);
            player.setExperience(0.0);
            player.setCurrency(0.0);
            player.setTotalPlayTime(0.0);
            player.setActive(true);
        }

        // Update player stats
        double gameTime = moves * 0.5; // Estimate 30 seconds per move
        player.setTotalPlayTime(player.getTotalPlayTime() + gameTime);
        
        if (gameWon) {
            // Bonus for winning
            int experienceGain = Math.max(50 - moves, 10); // Better score for fewer moves
            player.setExperience(player.getExperience() + experienceGain);
            player.setCurrency(player.getCurrency() + 10);
            
            // Level up logic
            if (player.getExperience() >= player.getLevel() * 100) {
                player.setLevel(player.getLevel() + 1);
            }
        } else {
            // Small participation reward
            player.setExperience(player.getExperience() + 5);
            player.setCurrency(player.getCurrency() + 2);
        }

        player.setLastPlayDate(java.time.LocalDate.now());
        playerRepository.save(player);
    }

    public void updatePlayerProgressDetailed(Long userId, Long gameId, String result, int moves, double gameTime, int experience, int currency) {
        // Find or create player for the specific game
        Optional<Player> existingPlayer = playerRepository.findByUserIdAndGameId(userId, gameId);
        
        Player player;
        if (existingPlayer.isPresent()) {
            player = existingPlayer.get();
        } else {
            player = new Player();
            player.setUserId(userId);
            player.setGameId(gameId);
            player.setPlayerName("naveen"); // Default player name
            player.setLevel(1);
            player.setExperience(0.0);
            player.setCurrency(0.0);
            player.setTotalPlayTime(0.0);
            player.setActive(true);
        }

        // Update player stats
        player.setTotalPlayTime(player.getTotalPlayTime() + (gameTime / 60.0)); // Convert minutes to hours
        player.setExperience(player.getExperience() + experience);
        player.setCurrency(player.getCurrency() + currency);
        
        // Level up logic - level up every 200 XP
        int newLevel = (int) (player.getExperience() / 200) + 1;
        if (newLevel > player.getLevel()) {
            player.setLevel(newLevel);
        }

        player.setLastPlayDate(java.time.LocalDate.now());
        playerRepository.save(player);
    }
}