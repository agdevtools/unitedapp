package com.football.unitedapp.s3;

import com.football.unitedapp.repository.TeamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/s3/buckets")
    public String getBuckets()
    {
        return s3Service.getBuckets();
    }

    @GetMapping("/s3/download")
    public String downloadFile() throws IOException {
        return s3Service.downloadFile();
    }

    @GetMapping("/s3/details")
    public List<TeamEntity> getFileDetails() throws IOException {
        return s3Service.getFileDetails();
    }

    @GetMapping("/s3/process")
    @ResponseStatus(HttpStatus.CREATED)
    public void processFile() throws IOException {
        s3Service.processFile();
    }

}
