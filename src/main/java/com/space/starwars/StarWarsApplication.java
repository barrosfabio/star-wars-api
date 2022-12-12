package com.space.starwars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class StarWarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarWarsApplication.class, args);
	}

}
