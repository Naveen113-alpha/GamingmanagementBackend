package com.examly.springapp.controller;

import com.examly.springapp.model.Tournament;
import com.examly.springapp.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@CrossOrigin(origins = "*")
public class TournamentController {

    @Autowired
    private TournamentRepository tournamentRepository;

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        Tournament savedTournament = tournamentRepository.save(tournament);
        return ResponseEntity.ok(savedTournament);
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAll();
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return tournamentRepository.findById(id)
                .map(tournament -> ResponseEntity.ok(tournament))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody Tournament tournament) {
        return tournamentRepository.findById(id)
                .map(existingTournament -> {
                    existingTournament.setName(tournament.getName());
                    existingTournament.setGame(tournament.getGame());
                    existingTournament.setPrize(tournament.getPrize());
                    existingTournament.setParticipants(tournament.getParticipants());
                    existingTournament.setStartDate(tournament.getStartDate());
                    existingTournament.setEndDate(tournament.getEndDate());
                    existingTournament.setStatus(tournament.getStatus());
                    existingTournament.setPoster(tournament.getPoster());
                    existingTournament.setDescription(tournament.getDescription());
                    existingTournament.setRegistrationOpen(tournament.getRegistrationOpen());
                    existingTournament.setRegistrationDeadline(tournament.getRegistrationDeadline());
                    existingTournament.setEntryFee(tournament.getEntryFee());
                    existingTournament.setMaxTeamSize(tournament.getMaxTeamSize());
                    existingTournament.setGameMode(tournament.getGameMode());
                    existingTournament.setPlatform(tournament.getPlatform());
                    return ResponseEntity.ok(tournamentRepository.save(existingTournament));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTournament(@PathVariable Long id) {
        return tournamentRepository.findById(id)
                .map(tournament -> {
                    tournamentRepository.delete(tournament);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}