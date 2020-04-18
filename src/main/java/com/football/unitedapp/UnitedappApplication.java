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
		System.setProperty("spring.profiles.active", "local");
		SpringApplication.run(UnitedappApplication.class, args);
		logger.info("**********     Starting United Application    ***********");

		logger.info(" ____ ___      .__  __             .___ _____                 \n" +
				"|    |   \\____ |__|/  |_  ____   __| _//  _  \\ ______ ______  \n" +
				"|    |   /    \\|  \\   __\\/ __ \\ / __ |/  /_\\  \\\\____ \\\\____ \\ \n" +
				"|    |  /   |  \\  ||  | \\  ___// /_/ /    |    \\  |_> >  |_> >\n" +
				"|______/|___|  /__||__|  \\___  >____ \\____|__  /   __/|   __/ \n" +
				"             \\/              \\/     \\/       \\/|__|   |__|    ");
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
