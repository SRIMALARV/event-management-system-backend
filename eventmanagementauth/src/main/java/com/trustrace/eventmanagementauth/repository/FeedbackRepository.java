package com.trustrace.eventmanagementauth.repository;

import com.trustrace.eventmanagementauth.models.Feedback;
import com.trustrace.eventmanagementauth.models.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
}
