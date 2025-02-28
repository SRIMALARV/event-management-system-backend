package com.trustrace.eventmanagementauth.security.services;

import com.trustrace.eventmanagementauth.models.ERole;
import com.trustrace.eventmanagementauth.models.Role;
import com.trustrace.eventmanagementauth.models.User;
import com.trustrace.eventmanagementauth.repository.RoleRepository;
import com.trustrace.eventmanagementauth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getUsersByRole(String roleName) {
        Optional<Role> role = roleRepository.findByName(ERole.valueOf(roleName));
        if (role.isEmpty()) {
            return List.of();
        }

        String roleId = role.get().getId();
        return userRepository.findByRoles(roleId);
    }
}
