package com.trustrace.eventmanagementauth.controllers;

import com.trustrace.eventmanagementauth.models.User;
import com.trustrace.eventmanagementauth.security.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/institutes")
    public List<String> getInstituteNames() {
        List<User> orgUsers = userService.getUsersByRole("ROLE_ORGANIZATION");
        return orgUsers.stream().map(User::getUsername).toList();
    }
}
