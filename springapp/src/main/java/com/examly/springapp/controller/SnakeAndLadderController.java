package com.examly.springapp.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.examly.springapp.service.SnakeAndLadderService;
import java.util.*;

@RestController
@RequestMapping("/api/snake-ladder")
@CrossOrigin(origins = "*")
public class SnakeAndLadderController {

    @Autowired
    private SnakeAndLadderService snakeAndLadderService;

    private final Map<Integer, Integer> snakes = Map.of(
        16, 6, 47, 26, 49, 11, 56, 53, 62, 19, 64, 60, 87, 24, 93, 73, 95, 75, 98, 78
    );
    
    private final Map<Integer, Integer> ladders = Map.of(
        1, 38, 4, 14, 9, 21, 21, 42, 28, 84, 36, 44, 51, 67, 71, 91, 80, 100
    );

    @PostMapping("/roll-dice")
    public GameMoveResponse rollDice(@RequestBody GameMoveRequest request) {
        int diceValue = new Random().nextInt(6) + 1;
        int currentPosition = request.getCurrentPosition();
        int newPosition = currentPosition + diceValue;
        
        if (newPosition > 100) {
            newPosition = currentPosition;
        }
        
        String moveType = "normal";
        if (snakes.containsKey(newPosition)) {
            newPosition = snakes.get(newPosition);
            moveType = "snake";
        } else if (ladders.containsKey(newPosition)) {
            newPosition = ladders.get(newPosition);
            moveType = "ladder";
        }
        
        boolean gameWon = newPosition == 100;
        
        // Save progress if game is won
        if (gameWon && request.getUserId() != null) {
            snakeAndLadderService.updatePlayerProgress(
                request.getUserId(), 
                request.getPlayerName(), 
                request.getMoves(), 
                true
            );
        }
        
        return new GameMoveResponse(diceValue, newPosition, moveType, gameWon, request.getPlayerName());
    }

    @PostMapping("/computer-move")
    public GameMoveResponse computerMove(@RequestBody GameMoveRequest request) {
        // Add slight delay for computer move to make it feel more natural
        try {
            Thread.sleep(1000 + new Random().nextInt(1500)); // 1-2.5 second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        int diceValue = new Random().nextInt(6) + 1;
        int currentPosition = request.getCurrentPosition();
        int newPosition = currentPosition + diceValue;
        
        if (newPosition > 100) {
            newPosition = currentPosition;
        }
        
        String moveType = "normal";
        if (snakes.containsKey(newPosition)) {
            newPosition = snakes.get(newPosition);
            moveType = "snake";
        } else if (ladders.containsKey(newPosition)) {
            newPosition = ladders.get(newPosition);
            moveType = "ladder";
        }
        
        boolean gameWon = newPosition == 100;
        
        // Save progress if computer wins
        if (gameWon && request.getUserId() != null) {
            snakeAndLadderService.updatePlayerProgress(
                request.getUserId(), 
                "Player", 
                request.getMoves(), 
                false // Player lost to computer
            );
        }
        
        return new GameMoveResponse(diceValue, newPosition, moveType, gameWon, "Computer");
    }

    @PostMapping("/update-progress")
    public Map<String, Object> updateProgress(@RequestBody ProgressUpdateRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            snakeAndLadderService.updatePlayerProgressDetailed(
                request.getUserId(),
                request.getGameId(),
                request.getResult(),
                request.getMoves(),
                request.getGameTime(),
                request.getExperience(),
                request.getCurrency()
            );
            response.put("success", true);
            response.put("message", "Progress updated successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update progress: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/board-config")
    public BoardConfig getBoardConfig() {
        return new BoardConfig(snakes, ladders);
    }

    public static class GameMoveRequest {
        private int currentPosition;
        private String playerName;
        private Long userId;
        private int moves;
        
        public int getCurrentPosition() { return currentPosition; }
        public void setCurrentPosition(int currentPosition) { this.currentPosition = currentPosition; }
        
        public String getPlayerName() { return playerName; }
        public void setPlayerName(String playerName) { this.playerName = playerName; }
        
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        
        public int getMoves() { return moves; }
        public void setMoves(int moves) { this.moves = moves; }
    }

    public static class GameMoveResponse {
        private int diceValue;
        private int newPosition;
        private String moveType;
        private boolean gameWon;
        private String playerName;
        
        public GameMoveResponse(int diceValue, int newPosition, String moveType, boolean gameWon, String playerName) {
            this.diceValue = diceValue;
            this.newPosition = newPosition;
            this.moveType = moveType;
            this.gameWon = gameWon;
            this.playerName = playerName;
        }
        
        public int getDiceValue() { return diceValue; }
        public int getNewPosition() { return newPosition; }
        public String getMoveType() { return moveType; }
        public boolean isGameWon() { return gameWon; }
        public String getPlayerName() { return playerName; }
    }

    public static class ProgressUpdateRequest {
        private Long userId;
        private Long gameId;
        private String result;
        private int moves;
        private double gameTime;
        private int experience;
        private int currency;
        
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        
        public Long getGameId() { return gameId; }
        public void setGameId(Long gameId) { this.gameId = gameId; }
        
        public String getResult() { return result; }
        public void setResult(String result) { this.result = result; }
        
        public int getMoves() { return moves; }
        public void setMoves(int moves) { this.moves = moves; }
        
        public double getGameTime() { return gameTime; }
        public void setGameTime(double gameTime) { this.gameTime = gameTime; }
        
        public int getExperience() { return experience; }
        public void setExperience(int experience) { this.experience = experience; }
        
        public int getCurrency() { return currency; }
        public void setCurrency(int currency) { this.currency = currency; }
    }

    public static class BoardConfig {
        private Map<Integer, Integer> snakes;
        private Map<Integer, Integer> ladders;
        
        public BoardConfig(Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
            this.snakes = snakes;
            this.ladders = ladders;
        }
        
        public Map<Integer, Integer> getSnakes() { return snakes; }
        public Map<Integer, Integer> getLadders() { return ladders; }
    }
}