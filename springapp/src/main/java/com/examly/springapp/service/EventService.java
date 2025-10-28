package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.Event;
import com.examly.springapp.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Create new event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Update event
    public Optional<Event> updateEvent(Long id, Event updatedEvent) {
        return eventRepository.findById(id).map(event -> {
            event.setGameId(updatedEvent.getGameId());
            event.setEventName(updatedEvent.getEventName());
            event.setStartDate(updatedEvent.getStartDate());
            event.setEndDate(updatedEvent.getEndDate());
            event.setEventType(updatedEvent.getEventType());
            event.setConfiguration(updatedEvent.getConfiguration());
            event.setActive(updatedEvent.isActive());
            return eventRepository.save(event);
        });
    }

    // Delete event
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // Get events by Game ID
    public List<Event> getEventsByGameId(Long gameId) {
        return eventRepository.findByGameId(gameId);
    }

    // Get events by type
    public List<Event> getEventsByType(String eventType) {
        return eventRepository.findByEventType(eventType);
    }

    // Get active/inactive events
    public List<Event> getEventsByActiveStatus(boolean isActive) {
        return eventRepository.findByIsActive(isActive);
    }
}
