package com.golden_dobakhe.HakPle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HakPleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HakPleApplication.class, args);
	}

}