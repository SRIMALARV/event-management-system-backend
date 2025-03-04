package com.trustrace.eventmanagementauth.controllers;

import com.trustrace.eventmanagementauth.models.User;
import com.trustrace.eventmanagementauth.payload.response.MessageResponse;
import com.trustrace.eventmanagementauth.repository.UserRepository;
import com.trustrace.eventmanagementauth.security.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @GetMapping("/institutes")
    public List<String> getInstituteNames() {
        List<User> orgUsers = userService.getUsersByRole("ROLE_ORGANIZATION");
        return orgUsers.stream().map(User::getUsername).toList();
    }

    @GetMapping("/institutes/details")
    public List<Map<String, String>> getInstituteDetails() {
        List<User> orgUsers = userService.getUsersByRole("ROLE_ORGANIZATION");

        return orgUsers.stream()
                .map(user -> Map.of(
                        "name", user.getUsername(),
                        "email", user.getEmail()
                ))
                .toList();
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        userRepository.delete(user);

        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }
}
