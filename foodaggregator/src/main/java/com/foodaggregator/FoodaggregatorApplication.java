package com.foodaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.foodaggregator")
public class FoodaggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodaggregatorApplication.class, args);
	}

}
