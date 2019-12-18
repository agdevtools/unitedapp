package com.football.unitedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import(AppConfig.class)
@ComponentScan(value = "com.football.unitedapp")
@SpringBootApplication
public class UnitedappApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnitedappApplication.class, args);
	}

}
