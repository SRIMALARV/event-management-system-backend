package com.trustrace.eventmanagementauth.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trustrace.eventmanagementauth.models.User;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("{ 'roles': ?0 }")
    List<User> findByRoles(String roleId);
}
