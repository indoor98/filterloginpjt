package com.toy.filterloginpjt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FilterloginpjtApplication {
	public static void main(String[] args) {
		SpringApplication.run(FilterloginpjtApplication.class, args);
	}
}
