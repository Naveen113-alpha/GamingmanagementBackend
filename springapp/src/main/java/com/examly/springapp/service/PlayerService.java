package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.Player;
import com.examly.springapp.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    // ✅ Get all players (no pagination)
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // ✅ Get paginated and sorted players
    public Page<Player> getPlayersPaged(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return playerRepository.findAll(pageable);
    }

    // ✅ Get player by ID
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    // ✅ Create player
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    // ✅ Update player
    public Optional<Player> updatePlayer(Long id, Player updatedPlayer) {
        return playerRepository.findById(id).map(player -> {
            player.setUserId(updatedPlayer.getUserId());
            player.setGameId(updatedPlayer.getGameId());
            player.setPlayerName(updatedPlayer.getPlayerName());
            player.setLevel(updatedPlayer.getLevel());
            player.setExperience(updatedPlayer.getExperience());
            player.setCurrency(updatedPlayer.getCurrency());
            player.setLastPlayDate(updatedPlayer.getLastPlayDate());
            player.setTotalPlayTime(updatedPlayer.getTotalPlayTime());
            player.setActive(updatedPlayer.isActive());
            return playerRepository.save(player);
        });
    }

    // ✅ Delete player
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    // ✅ Get all players by user ID
    public List<Player> getPlayersByUserId(Long userId) {
        return playerRepository.findByUserId(userId);
    }

    // ✅ Get all players by game ID
    public List<Player> getPlayersByGameId(Long gameId) {
        return playerRepository.findByGameId(gameId);
    }
}
