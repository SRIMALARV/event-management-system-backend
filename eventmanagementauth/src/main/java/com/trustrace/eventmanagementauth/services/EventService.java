package com.trustrace.eventmanagementauth.services;

import com.trustrace.eventmanagementauth.models.Event;
import com.trustrace.eventmanagementauth.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventService {

    private final MongoTemplate mongoTemplate;

    private EventRepository eventRepository;

    @Autowired
    private JavaMailSender mailSender;

    public EventService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Event createEvent(Event event, String username) {
        event.setCreatedBy(username);
        event.setCompletionStatus("incomplete");
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

    public void updateCompletionStatus(String eventId, String completionStatus) {
        if (!completionStatus.equalsIgnoreCase("completed") && !completionStatus.equalsIgnoreCase("incomplete")) {
            throw new IllegalArgumentException("Invalid completion status. Allowed values: completed, incomplete.");
        }

        Query query = new Query(Criteria.where("id").is(eventId));
        Update update = new Update().set("completionStatus", completionStatus);
        mongoTemplate.updateFirst(query, update, Event.class);

        Event event = getEventById(eventId);
        sendCompletionStatusEmail(event, completionStatus);
    }

    private void sendCompletionStatusEmail(Event event, String completionStatus) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hubevent54@gmail.com");
        message.setTo(event.getCreatorEmail());
        message.setSubject(" Thank You for Hosting a Wonderful Event: " + event.getTitle());

        if ("completed".equalsIgnoreCase(completionStatus)) {
            String emailBody = "Dear " + event.getCreatedBy() + ",\n\n" +
                    "We sincerely thank you for hosting and organising the event '" + event.getTitle() + "' successfully on " + event.getEventDate()
                    + ".\n\n" +
                    "Best Regards,\nEvent Hub Team";

            message.setText(emailBody);
            mailSender.send(message);
            System.out.println("Completion status email sent to " + event.getCreatorEmail());
        }
    }

}