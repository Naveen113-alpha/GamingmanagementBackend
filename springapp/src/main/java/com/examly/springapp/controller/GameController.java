    package com.examly.springapp.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import com.examly.springapp.model.Game;
    import com.examly.springapp.service.GameService;

    import java.util.List;
    import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


    @RestController
    @RequestMapping("/api/games")
    @CrossOrigin(origins = "*")
    public class GameController {

        @Autowired
        private GameService gameService;
        @PostMapping
        public Game createGame(@RequestBody Game game) {
            return gameService.createGame(game);
        }
        
        // Get all games
        @GetMapping
        public List<Game> getAllGames() {
            return gameService.getAllGames();
        }

        // Get game by ID
        @GetMapping("/{id}")
        public Optional<Game> getGameById(@PathVariable Long id) {
            return gameService.getGameById(id);
        }

    

        // Update game
        @PutMapping("/{id}")
        public Optional<Game> updateGame(@PathVariable Long id, @RequestBody Game game) {
            return gameService.updateGame(id, game);
        }

        // Delete game
        @DeleteMapping("/{id}")
        public String deleteGame(@PathVariable Long id) {
            gameService.deleteGame(id);
            return "Game with ID " + id + " deleted successfully.";
        }
    }
