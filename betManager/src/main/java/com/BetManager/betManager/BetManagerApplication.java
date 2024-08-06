package com.BetManager.betManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"controller", "controller"})
@EnableMongoRepositories(basePackages = "repository")
@ComponentScan(basePackages = {"model", "service", "repository"})
public class BetManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetManagerApplication.class, args);
	}

}