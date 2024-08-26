package com.abc.pushtrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.abc"})
public class PushtripApplication {

	public static void main(String[] args) {
		SpringApplication.run(PushtripApplication.class, args);
	}
}
