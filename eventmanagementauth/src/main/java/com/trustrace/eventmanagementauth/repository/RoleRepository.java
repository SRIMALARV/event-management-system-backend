package com.trustrace.eventmanagementauth.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trustrace.eventmanagementauth.models.ERole;
import com.trustrace.eventmanagementauth.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
