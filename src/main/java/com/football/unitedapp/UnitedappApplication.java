package com.football.unitedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.Date;
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
		printStack();

	}

	public static String getOperatingSystem() {
		String os = System.getProperty("os.name");
		System.out.println("Using System Property: " + os);
		return os;
	}

	public static void setOperatingSystem() {
		if (getOperatingSystem().toLowerCase().contains("mac")) {
			System.setProperty("spring.profiles.active", "local");
		} else {
			System.setProperty("spring.profiles.active", "prod");
		}
	}

	public static void printStack() {
		try {
			while (true) {
				System.out.println("********  Still Up at " + new Date() + "  **********");
				Thread.sleep(3 * 50000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
