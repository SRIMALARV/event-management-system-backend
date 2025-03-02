package com.trustrace.eventmanagementauth.services;

import com.trustrace.eventmanagementauth.models.Event;
import com.trustrace.eventmanagementauth.models.Registration;
import com.trustrace.eventmanagementauth.repository.EventRepository;
import com.trustrace.eventmanagementauth.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Registration saveRegistration(Registration registration) {
        Registration savedRegistration = registrationRepository.save(registration);

        Event event = eventRepository.findById(registration.getEventId()).orElse(null);

        if (event != null) {
            sendEventDetailsEmail(registration.getEmail(), event);
        }

        return savedRegistration;
    }

    public List<Registration> getRegistrationsByEventId(String eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public List<Registration> getRegistrationsByInstitution(String instituteName) {
        return registrationRepository.findByInstituteName(instituteName);
    }

    public List<Registration> getRegistrationsByUsername(String username) {
        return registrationRepository.findByUsername(username);
    }
    private void sendEventDetailsEmail(String recipientEmail, Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hubevent54@gmail.com");
        message.setTo(recipientEmail);
        message.setSubject("Event Registration Confirmation: " + event.getTitle());

        String emailBody = "Hello,\n" +
                "Thank you for registering for " + event.getTitle() + "!\n\n" +
                "Date: " + event.getEventDate() + "\n" +
                "Time: " + event.getEventTime() + "\n\n" +
                "Meet URL: " + event.getMeetUrl() + "\n" +
                "Meeting ID: " + event.getMeetId() + "\n" +
                "Passcode: " + event.getMeetPasscode() + "\n\n" +
                "See you at the event!\n\n" +
                "Best Regards,\nEvent Hub Team";

        message.setText(emailBody);

        mailSender.send(message);
        System.out.println("Email sent successfully to " + recipientEmail);
    }

}
