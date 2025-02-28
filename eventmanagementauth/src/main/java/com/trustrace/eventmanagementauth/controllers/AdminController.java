package com.trustrace.eventmanagementauth.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.trustrace.eventmanagementauth.models.ERole;
import com.trustrace.eventmanagementauth.models.Role;
import com.trustrace.eventmanagementauth.models.User;
import com.trustrace.eventmanagementauth.payload.request.SignupRequest;
import com.trustrace.eventmanagementauth.payload.response.MessageResponse;
import com.trustrace.eventmanagementauth.repository.RoleRepository;
import com.trustrace.eventmanagementauth.repository.UserRepository;

import org.springframework.security.access.prepost.PreAuthorize;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/create-org")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createOrgUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role orgRole = roleRepository.findByName(ERole.ROLE_ORGANIZATION)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(orgRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Organization user created successfully!"));
    }
}
