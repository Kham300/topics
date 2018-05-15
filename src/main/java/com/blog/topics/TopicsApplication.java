package com.blog.topics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.blog.topics")
@SpringBootApplication
public class TopicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopicsApplication.class, args);
	}
}
