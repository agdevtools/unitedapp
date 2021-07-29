package com.football.unitedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import java.util.logging.Logger;

@Import(AppConfig.class)
@ComponentScan(value = "com.football.unitedapp")
@SpringBootApplication
public class UnitedappApplication {
	private static final Logger logger = Logger.getLogger(UnitedappApplication.class.getName());
	public static void main(String[] args) {
		setOperatingSystem();
		SpringApplication.run(UnitedappApplication.class, args);
		logger.info("**********     Starting United Application    ***********");
		logger.info("**********     on " + getOperatingSystem() +  " ***********");
	}

	public static String getOperatingSystem() {
		String os = System.getProperty("os.name");
		logger.info("Using System Property: " + os);
		return os;
	}

	public static void setOperatingSystem() {
		if (getOperatingSystem().toLowerCase().contains("mac")) {
			System.setProperty("spring.profiles.active", "local");
		} else {
			System.setProperty("spring.profiles.active", "prod");
		}
	}

}
