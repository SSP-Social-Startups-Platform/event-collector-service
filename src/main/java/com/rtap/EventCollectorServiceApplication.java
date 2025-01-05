package com.rtap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The EventCollectorServiceApplication class serves as the entry point for the Spring Boot application.
 * It initializes and launches the application by invoking the SpringApplication.run() method.
 */
@SpringBootApplication
public class EventCollectorServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventCollectorServiceApplication.class, args);
  }
}
