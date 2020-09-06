package com.java.appbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AppNameApplication {

	public static void main(String[] args) {
        SpringApplication.run(AppNameApplication.class, args);
		
		System.out.println("New Spring Security Application");

	}

}
