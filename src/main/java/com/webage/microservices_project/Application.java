package com.webage.microservices_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application.
 * <p>
 * This class contains the main method which is used to launch the Spring Boot application.
 * The {@link SpringApplication} class is used to bootstrap the application, initialize the Spring context,
 * and start the embedded server.
 * </p>
 */
@SpringBootApplication
public class Application {

    /**
     * Main method that serves as the entry point for the Spring Boot application.
     * 
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}