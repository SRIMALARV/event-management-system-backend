package com.trustrace.eventmanagementauth.services;

import com.trustrace.eventmanagementauth.models.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final MongoTemplate mongoTemplate;

    public EventService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Event createEvent(Event event, String username) {
        event.setCreatedBy(username);
        return mongoTemplate.save(event);
    }

    public List<Event> getAllEvents() {
        return mongoTemplate.findAll(Event.class);
    }

    public Event getEventById(String id) {
        Event event = mongoTemplate.findById(id, Event.class);
        if (event == null) {
            throw new RuntimeException("Event not found with ID: " + id);
        }
        return event;
    }

    public List<Event> getEventsByType(String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        return mongoTemplate.find(query, Event.class);
    }

    public List<Event> getSortedEvents(String order) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Query query = new Query().with(Sort.by(direction, "eventDate"));
        return mongoTemplate.find(query, Event.class);
    }

    public List<Event> getEventsByTypeSorted(String type, String order) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        query.with(Sort.by(direction, "eventDate"));
        return mongoTemplate.find(query, Event.class);
    }

    public List<Event> getEventsByUser(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createdBy").is(username));
        return mongoTemplate.find(query, Event.class);
    }

    public List<Event> getApprovedEvents() {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("approved"));
        return mongoTemplate.find(query, Event.class);
    }
}
