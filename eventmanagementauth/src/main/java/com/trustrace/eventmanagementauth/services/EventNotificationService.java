package com.trustrace.eventmanagementauth.services;

import com.trustrace.eventmanagementauth.models.Event;
import com.trustrace.eventmanagementauth.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query; // ✅ Correct import
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class EventNotificationService {
    private final MongoTemplate mongoTemplate;
    private final JavaMailSender mailSender;
    private final EventRepository eventRepository;

    @Autowired
    public EventNotificationService(MongoTemplate mongoTemplate, EventRepository eventRepository, JavaMailSender mailSender) { // ✅ Fixed constructor name
        this.mongoTemplate = mongoTemplate;
        this.eventRepository = eventRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendEventReminderEmails() {
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));

        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.of("UTC")).toInstant());
        Date endOfDay = Date.from(today.plusDays(1).atStartOfDay(ZoneId.of("UTC")).toInstant());

        Query query = new Query();
        query.addCriteria(Criteria.where("eventDate").gte(startOfDay).lt(endOfDay));
        List<Event> events = mongoTemplate.find(query, Event.class);

        for (Event event : events) {
            sendReminderEmail(event);
        }
    }


    private void sendReminderEmail(Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hubevent54@gmail.com");
        message.setTo(event.getCreatorEmail());
        message.setSubject("Reminder: Your event is happening today!");

        String emailBody = "Dear " + event.getCreatedBy() + ",\n\n" +
                "This is a reminder that your event '" + event.getTitle() + "' is scheduled for today.\n\n" +
                "Make sure everything is set and ready!\n\n" +
                "Best Regards,\nEvent Hub Team";

        message.setText(emailBody);
        mailSender.send(message);
        System.out.println("Reminder email sent to " + event.getCreatorEmail());
    }
}
