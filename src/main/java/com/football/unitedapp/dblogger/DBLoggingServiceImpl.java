package com.football.unitedapp.dblogger;

import com.football.unitedapp.repository.LogsEntity;
import com.football.unitedapp.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DBLoggingServiceImpl implements DBLoggingService{

    private LogsRepository logsRepository;

    @Autowired
    public DBLoggingServiceImpl(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    public void logToDatabase(Date datestamp, String endpoint, String method, String requestBody) {
        LogsEntity logsEntity = new LogsEntity(datestamp,endpoint,method,requestBody);

        logsRepository.save(logsEntity);
    }


}
