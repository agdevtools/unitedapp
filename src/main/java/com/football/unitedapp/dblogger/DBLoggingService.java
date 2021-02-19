package com.football.unitedapp.dblogger;

import java.util.Date;

public interface DBLoggingService {
    void logToDatabase(Date datestamp, String endpoint, String method, String request_body);
}
