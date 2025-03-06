package com.trustrace.eventmanagementauth.controllers;

import com.trustrace.eventmanagementauth.models.Event;
import com.trustrace.eventmanagementauth.services.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/{organization}")
    @PreAuthorize("hasAnyRole('ORGANIZATION', 'ADMIN')")
    public ResponseEntity<List<Event>> getEventsByOrganization(@PathVariable String organization) {
        String decodedOrganization = URLDecoder.decode(organization, StandardCharsets.UTF_8);
        List<Event> events = organizationService.getEventsByOrganization(decodedOrganization);
        return ResponseEntity.ok(events);
    }


    @PutMapping("/{eventId}/{status}")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<Event> updateEventStatus(@PathVariable String eventId,
                                                   @PathVariable String status,
                                                   @RequestBody(required = false) Map<String, String> requestBody) {
        String reason = requestBody != null ? requestBody.get("reason") : null;
        Event updatedEvent = organizationService.updateEventStatus(eventId, status, reason);
        return ResponseEntity.ok(updatedEvent);
    }
}