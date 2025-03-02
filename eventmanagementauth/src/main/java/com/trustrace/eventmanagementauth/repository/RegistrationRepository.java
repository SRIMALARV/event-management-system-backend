package com.trustrace.eventmanagementauth.repository;


import com.trustrace.eventmanagementauth.models.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegistrationRepository extends MongoRepository<Registration, String> {

    List<Registration> findByEventId(String eventId);

    List<Registration> findByInstituteName(String instituteName);

    List<Registration> findByUsername(String username);
}
