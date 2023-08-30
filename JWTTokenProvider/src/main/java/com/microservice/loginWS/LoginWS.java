package com.microservice.loginWS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@SpringBootApplication
@EnableMongoRepositories
public class LoginWS {
	public static void main(String[] args) {
		SpringApplication.run(LoginWS.class, args);
	}
}
