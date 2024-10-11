package com.example.retail_rocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.retail_rocket.repository")
public class RetailRocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailRocketApplication.class, args);
	}

}
