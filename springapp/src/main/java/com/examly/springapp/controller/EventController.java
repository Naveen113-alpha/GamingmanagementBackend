package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.examly.springapp.model.Event;
import com.examly.springapp.service.EventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    // Get all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Get event by ID
    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    // Create new event
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    // Update event
    @PutMapping("/{id}")
    public Optional<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    // Delete event
    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "Event with ID " + id + " deleted successfully.";
    }

    // Get events by Game ID
    @GetMapping("/game/{gameId}")
    public List<Event> getEventsByGameId(@PathVariable Long gameId) {
        return eventService.getEventsByGameId(gameId);
    }

    // Get events by type
    @GetMapping("/type/{eventType}")
    public List<Event> getEventsByType(@PathVariable String eventType) {
        return eventService.getEventsByType(eventType);
    }

    // Get active/inactive events
    @GetMapping("/active/{status}")
    public List<Event> getEventsByActiveStatus(@PathVariable boolean status) {
        return eventService.getEventsByActiveStatus(status);
    }
}
