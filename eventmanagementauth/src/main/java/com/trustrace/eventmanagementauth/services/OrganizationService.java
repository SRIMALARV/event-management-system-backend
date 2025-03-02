package com.trustrace.eventmanagementauth.services;

import com.trustrace.eventmanagementauth.models.Event;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final MongoTemplate mongoTemplate;

    public OrganizationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Event> getEventsByOrganization(String organization) {
        Query query = new Query();
        query.addCriteria(Criteria.where("instituteName").is(organization));
        return mongoTemplate.find(query, Event.class);
    }

    public Event updateEventStatus(String eventId, String status) {
        Query query = new Query(Criteria.where("_id").is(eventId));
        return mongoTemplate.findAndModify(query,
                new org.springframework.data.mongodb.core.query.Update().set("status", status),
                Event.class);
    }
}