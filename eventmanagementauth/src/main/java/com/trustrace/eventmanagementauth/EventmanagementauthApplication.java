package com.trustrace.eventmanagementauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventmanagementauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementauthApplication.class, args);
	}

}
