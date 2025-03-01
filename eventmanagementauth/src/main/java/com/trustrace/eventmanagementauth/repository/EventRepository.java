package com.trustrace.eventmanagementauth.repository;

import com.trustrace.eventmanagementauth.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByStatus(String status);
}

