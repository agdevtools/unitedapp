package com.football.unitedapp.util;

import com.football.unitedapp.dblogger.DBLoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class KafkaConsumer {

    private DBLoggingService dbLoggingService;

    @Autowired
    public KafkaConsumer(DBLoggingService dbLoggingService) {
        this.dbLoggingService = dbLoggingService;
    }

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "footiestats", groupId = "unitedapp")
    public void consume(String message){

        logger.info(String.format("$$$ -> Consumed footiestats Message -> %s",message));
        dbLoggingService.logToDatabase(new Date(System.currentTimeMillis()),getEndPoint(message), "GET","");

    }

    private String getEndPoint(String message) {
        String parsedMessage = message.substring(message.lastIndexOf('.') + 1).trim();

        switch (parsedMessage) {
            case "getForm())":
                return "footiestats/api/form";
            case "getNextFixtures())":
                return "footiestats/api/next";
            case "getLeagueTable())":
                return "footiestats/api/league";
            default:
                return "kafka Error no match found";
        }

    }
}
