package com.trustrace.eventmanagementauth.controllers;


import com.trustrace.eventmanagementauth.models.Registration;
import com.trustrace.eventmanagementauth.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/all")
    public List<Registration> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    @GetMapping("/monthly/{year}")
    public List<Map<String, Object>> getMonthlyRegistrationsByYear(@PathVariable int year) {
        return registrationService.getMonthlyRegistrationsByYear(year);
    }


    @PostMapping("/save")
    public Registration saveRegistration(@RequestBody Registration registration) {
        return registrationService.saveRegistration(registration);
    }

    @GetMapping("/event/{eventId}")
    public List<Registration> getRegistrationsByEvent(@PathVariable String eventId) {
        return registrationService.getRegistrationsByEventId(eventId);
    }

    @GetMapping("/institution/{instituteName}")
    public List<Registration> getRegistrationsByInstitution(@PathVariable String instituteName) {
        return registrationService.getRegistrationsByInstitution(instituteName);
    }

    @GetMapping("/user/{username}")
    public List<Registration> getRegistrationsByUsername(@PathVariable String username) {
        return registrationService.getRegistrationsByUsername(username);
    }
}
