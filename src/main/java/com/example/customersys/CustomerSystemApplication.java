package com.example.customersys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CustomerSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerSystemApplication.class, args);
	}

}
