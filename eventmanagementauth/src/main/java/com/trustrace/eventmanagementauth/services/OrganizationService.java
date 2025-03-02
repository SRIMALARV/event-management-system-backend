package com.trustrace.eventmanagementauth.services;

import com.trustrace.eventmanagementauth.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    private JavaMailSender mailSender;


    public OrganizationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Event> getEventsByOrganization(String organization) {
        Query query = new Query();
        query.addCriteria(Criteria.where("instituteName").is(organization));
        return mongoTemplate.find(query, Event.class);
    }

    public Event updateEventStatus(String eventId, String status, String reason) {
        Query query = new Query(Criteria.where("_id").is(eventId));
        Event updatedEvent = mongoTemplate.findAndModify(query,
                new org.springframework.data.mongodb.core.query.Update().set("status", status),
                Event.class);

        if (updatedEvent != null) {
            sendStatusUpdateEmail(updatedEvent, status, reason);
        }

        return updatedEvent;
    }

    private void sendStatusUpdateEmail(Event event, String status, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hubevent54@gmail.com");
        message.setTo(event.getCreatorEmail());
        message.setSubject("Event Status Update: " + event.getTitle());

        String emailBody = "Dear " + event.getCreatedBy() + ",\n\n" +
                "Your event '" + event.getTitle() + "' has been " + status.toLowerCase() + ".\n\n";

        if ("Rejected".equalsIgnoreCase(status) && reason != null) {
            emailBody += "Reason for rejection: " + reason + "\n\n";
        }
        else {
            emailBody += "You can view the registration details & proceed with the event." + "\n\n";
        }

        emailBody += "Best Regards,\nEvent Hub Team";

        message.setText(emailBody);
        mailSender.send(message);
        System.out.println("Status update email sent to " + event.getCreatorEmail());
    }

}