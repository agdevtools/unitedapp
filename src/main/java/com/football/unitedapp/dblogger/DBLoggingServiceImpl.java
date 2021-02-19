package com.football.unitedapp.dblogger;

import com.football.unitedapp.repository.LogsEntity;
import com.football.unitedapp.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DBLoggingServiceImpl implements DBLoggingService{

    @Autowired
    private LogsRepository logsRepository;

    public void logToDatabase(Date datestamp, String endpoint, String method, String request_body) {
        LogsEntity logsEntity = new LogsEntity(datestamp,endpoint,method,request_body);

        logsRepository.save(logsEntity);
    }


}
