package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SimpleRestApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(SimpleRestApiApplication.class, args);

    }

}