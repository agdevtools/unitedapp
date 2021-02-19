package com.football.unitedapp.repository;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="t_logs")
public class LogsEntity {
    @Id
    @GeneratedValue
    long id;
    @Column(name = "datestamp")
    Date datestamp;
    @Column(name = "endpoint")
    String endpoint;
    @Column(name = "method")
    String method;
    @Column(name = "request_body")
    String requestBody;

    public LogsEntity( Date datestamp, String endpoint, String method, String requestBody) {
        this.datestamp = datestamp;
        this.endpoint = endpoint;
        this.method = method;
        this.requestBody = requestBody;
    }

    public LogsEntity() {

    }
}
