package com.football.unitedapp.util;

import com.football.unitedapp.dblogger.DBLoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
@Profile({ "!test" })
@Component
public class RabbitMQConsumer {

    private DBLoggingService dbLoggingService;
    private final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    public RabbitMQConsumer(DBLoggingService dbLoggingService) {
        this.dbLoggingService = dbLoggingService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(String message) {
        logger.info(String.format("$$$ -> Consumed footiestats Message -> %s",message));
        dbLoggingService.logToDatabase(new Date(System.currentTimeMillis()),getEndPoint(message), "GET","");
    }

    String getEndPoint(String message) {
        String parsedMessage = message.substring(message.lastIndexOf('.') + 1).trim();

        switch (parsedMessage) {
            case "getForm())":
                return "footiestats/api/form";
            case "getNextFixtures())":
                return "footiestats/api/next";
            case "getLeagueTable())":
                return "footiestats/api/league";
            default:
                return "Rabbit Error no match found";
        }

    }
}
