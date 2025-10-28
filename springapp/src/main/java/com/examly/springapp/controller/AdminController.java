package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.examly.springapp.service.PlayerService;
import com.examly.springapp.service.UserService;
import com.examly.springapp.model.Player;
import com.examly.springapp.model.User;
import com.examly.springapp.model.GameRequest;
import com.examly.springapp.model.ApprovedGame;
import com.examly.springapp.repository.GameRequestRepository;
import com.examly.springapp.repository.ApprovedGameRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private GameRequestRepository gameRequestRepository;
    
    @Autowired
    private ApprovedGameRepository approvedGameRepository;

    @GetMapping("/dashboard")
    public AdminDashboard getDashboard() {
        List<User> users = userService.getAllUsers();
        List<Player> players = playerService.getAllPlayers();
        
        return new AdminDashboard(
            users.size(),
            players.size(),
            (int) players.stream().mapToDouble(Player::getTotalPlayTime).sum(),
            players.stream().mapToInt(Player::getLevel).max().orElse(0)
        );
    }

    @GetMapping("/users-with-progress")
    public List<UserProgress> getUsersWithProgress() {
        List<User> users = userService.getAllUsers();
        List<Player> players = playerService.getAllPlayers();
        
        return users.stream().map(user -> {
            List<Player> userPlayers = players.stream()
                .filter(p -> p.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
            
            return new UserProgress(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                userPlayers.size(),
                userPlayers.stream().mapToDouble(Player::getTotalPlayTime).sum(),
                userPlayers.stream().mapToDouble(Player::getExperience).sum(),
                userPlayers.stream().mapToDouble(Player::getCurrency).sum(),
                userPlayers.isEmpty() ? 0 : userPlayers.stream().mapToInt(Player::getLevel).sum() / userPlayers.size(),
                userPlayers
            );
        }).collect(Collectors.toList());
    }

    @GetMapping("/player-achievements/{userId}")
    public List<Achievement> getPlayerAchievements(@PathVariable Long userId) {
        List<Player> userPlayers = playerService.getPlayersByUserId(userId);
        
        double totalPlayTime = userPlayers.stream().mapToDouble(Player::getTotalPlayTime).sum();
        double totalExperience = userPlayers.stream().mapToDouble(Player::getExperience).sum();
        int maxLevel = userPlayers.stream().mapToInt(Player::getLevel).max().orElse(0);
        
        List<Achievement> achievements = new ArrayList<>();
        achievements.add(new Achievement("First Steps", "Started playing", true));
        achievements.add(new Achievement("Level 5", "Reached level 5", maxLevel >= 5));
        achievements.add(new Achievement("Level 10", "Reached level 10", maxLevel >= 10));
        achievements.add(new Achievement("Level 25", "Reached level 25", maxLevel >= 25));
        achievements.add(new Achievement("Experienced", "Gained 1000+ XP", totalExperience >= 1000));
        achievements.add(new Achievement("Expert", "Gained 5000+ XP", totalExperience >= 5000));
        achievements.add(new Achievement("Time Keeper", "Played for 10+ hours", totalPlayTime >= 10));
        achievements.add(new Achievement("Dedicated", "Played for 50+ hours", totalPlayTime >= 50));
        
        return achievements;
    }

    @GetMapping("/game-requests")
    public List<GameRequest> getAllGameRequests() {
        return gameRequestRepository.findAll();
    }

    @PostMapping("/game-requests")
    public GameRequest createGameRequest(@RequestBody GameRequestDto requestDto) {
        GameRequest gameRequest = new GameRequest(
            requestDto.getUserId(),
            requestDto.getGameId(),
            requestDto.getGameName(),
            requestDto.getGameIcon(),
            requestDto.getPlayerName(),
            "pending",
            LocalDate.now()
        );
        return gameRequestRepository.save(gameRequest);
    }

    @PutMapping("/game-requests/{id}/approve")
    public GameRequest approveGameRequest(@PathVariable Long id) {
        GameRequest request = gameRequestRepository.findById(id).orElseThrow();
        request.setStatus("approved");
        
        // Create approved game for the user
        ApprovedGame approvedGame = new ApprovedGame(
            request.getUserId(),
            request.getGameId(),
            request.getGameName(),
            request.getGameIcon(),
            "Medium",
            request.getGameId() == 1 ? "/snake-ladder" : null
        );
        approvedGameRepository.save(approvedGame);
        
        // Create player entry for progress tracking
        Player player = new Player();
        player.setUserId(request.getUserId());
        player.setGameId(request.getGameId());
        player.setPlayerName(request.getPlayerName());
        player.setLevel(1);
        player.setExperience(0);
        player.setCurrency(0);
        player.setTotalPlayTime(0.0);
        player.setActive(true);
        player.setLastPlayDate(LocalDate.now());
        playerService.createPlayer(player);
        
        return gameRequestRepository.save(request);
    }

    @PutMapping("/game-requests/{id}/reject")
    public GameRequest rejectGameRequest(@PathVariable Long id) {
        GameRequest request = gameRequestRepository.findById(id).orElseThrow();
        request.setStatus("rejected");
        return gameRequestRepository.save(request);
    }

    @GetMapping("/approved-games/{userId}")
    public List<ApprovedGame> getApprovedGames(@PathVariable Long userId) {
        return approvedGameRepository.findByUserId(userId);
    }


    @GetMapping("/test")
    public Map<String, String> testEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Admin API is working");
        return response;
    }

    public static class GameRequestDto {
        private Long userId;
        private Long gameId;
        private String gameName;
        private String gameIcon;
        private String playerName;
        
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        
        public Long getGameId() { return gameId; }
        public void setGameId(Long gameId) { this.gameId = gameId; }
        
        public String getGameName() { return gameName; }
        public void setGameName(String gameName) { this.gameName = gameName; }
        
        public String getGameIcon() { return gameIcon; }
        public void setGameIcon(String gameIcon) { this.gameIcon = gameIcon; }
        
        public String getPlayerName() { return playerName; }
        public void setPlayerName(String playerName) { this.playerName = playerName; }
    }

    public static class AdminDashboard {
        private int totalUsers;
        private int totalPlayers;
        private int totalPlayTime;
        private int maxLevel;
        
        public AdminDashboard(int totalUsers, int totalPlayers, int totalPlayTime, int maxLevel) {
            this.totalUsers = totalUsers;
            this.totalPlayers = totalPlayers;
            this.totalPlayTime = totalPlayTime;
            this.maxLevel = maxLevel;
        }
        
        public int getTotalUsers() { return totalUsers; }
        public int getTotalPlayers() { return totalPlayers; }
        public int getTotalPlayTime() { return totalPlayTime; }
        public int getMaxLevel() { return maxLevel; }
    }

    public static class UserProgress {
        private Long userId;
        private String username;
        private String email;
        private int gamesPlayed;
        private double totalPlayTime;
        private double totalExperience;
        private double totalCurrency;
        private double averageLevel;
        private List<Player> players;
        
        public UserProgress(Long userId, String username, String email, int gamesPlayed, 
                          double totalPlayTime, double totalExperience, double totalCurrency, 
                          double averageLevel, List<Player> players) {
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.gamesPlayed = gamesPlayed;
            this.totalPlayTime = totalPlayTime;
            this.totalExperience = totalExperience;
            this.totalCurrency = totalCurrency;
            this.averageLevel = averageLevel;
            this.players = players;
        }
        
        public Long getUserId() { return userId; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public int getGamesPlayed() { return gamesPlayed; }
        public double getTotalPlayTime() { return totalPlayTime; }
        public double getTotalExperience() { return totalExperience; }
        public double getTotalCurrency() { return totalCurrency; }
        public double getAverageLevel() { return averageLevel; }
        public List<Player> getPlayers() { return players; }
    }

    public static class Achievement {
        private String name;
        private String description;
        private boolean earned;
        
        public Achievement(String name, String description, boolean earned) {
            this.name = name;
            this.description = description;
            this.earned = earned;
        }
        
        public String getName() { return name; }
        public String getDescription() { return description; }
        public boolean isEarned() { return earned; }
    }
}