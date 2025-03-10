package com.trustrace.eventmanagementauth.controllers;

import com.trustrace.eventmanagementauth.models.Event;
import com.trustrace.eventmanagementauth.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        String username = getCurrentUsername();
        Event createdEvent = eventService.createEvent(event, username);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Event>> getApprovedEvents() {
        List<Event> approvedEvents = eventService.getApprovedEvents();
        return ResponseEntity.ok(approvedEvents);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable String id) {
        try {
            Event event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Event not found with ID: " + id);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Event>> getEventsByType(@PathVariable String type) {
        List<Event> events = eventService.getEventsByType(type);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/sorted/{order}")
    public ResponseEntity<List<Event>> getSortedEvents(@PathVariable String order) {
        List<Event> events = eventService.getSortedEvents(order);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/type/{type}/sorted/{order}")
    public ResponseEntity<List<Event>> getEventsByTypeSorted(@PathVariable String type, @PathVariable String order) {
        List<Event> events = eventService.getEventsByTypeSorted(type, order);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/my-events")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Event>> getMyEvents() {
        String username = getCurrentUsername();
        List<Event> myEvents = eventService.getEventsByUser(username);
        return ResponseEntity.ok(myEvents);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
