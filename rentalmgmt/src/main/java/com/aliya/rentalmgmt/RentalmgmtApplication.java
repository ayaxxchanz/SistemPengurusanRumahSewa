package com.aliya.rentalmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class RentalmgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentalmgmtApplication.class, args);
	}

}
