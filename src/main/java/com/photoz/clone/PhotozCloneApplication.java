package com.photoz.clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PhotozCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotozCloneApplication.class, args);
	}

}
