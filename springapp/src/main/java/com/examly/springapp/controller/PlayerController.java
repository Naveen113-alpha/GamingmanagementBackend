package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.examly.springapp.model.Player;
import com.examly.springapp.service.PlayerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // ✅ Get all players (without pagination)
    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    // ✅ Get players with pagination & sorting
    @GetMapping("/paged")
    public Page<Player> getPlayersPaged(
            @RequestParam(defaultValue = "0") int page,           // page number (0-indexed)
            @RequestParam(defaultValue = "5") int size,           // items per page
            @RequestParam(defaultValue = "id") String sortBy,     // field to sort by
            @RequestParam(defaultValue = "asc") String direction  // asc or desc
    ) {
        return playerService.getPlayersPaged(page, size, sortBy, direction);
    }

    // ✅ Get player by ID
    @GetMapping("/{id}")
    public Optional<Player> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    // ✅ Create player
    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    // ✅ Update player
    @PutMapping("/{id}")
    public Optional<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }

    // ✅ Delete player
    @DeleteMapping("/{id}")
    public String deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return "Player with ID " + id + " deleted successfully.";
    }

    // ✅ Get players by user ID
    @GetMapping("/user/{userId}")
    public List<Player> getPlayersByUserId(@PathVariable Long userId) {
        return playerService.getPlayersByUserId(userId);
    }

    // ✅ Get players by game ID
    @GetMapping("/game/{gameId}")
    public List<Player> getPlayersByGameId(@PathVariable Long gameId) {
        return playerService.getPlayersByGameId(gameId);
    }
}
