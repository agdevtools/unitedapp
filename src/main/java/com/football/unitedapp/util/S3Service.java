package com.football.unitedapp.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.iterable.S3Objects;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class S3Service {

    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIA2ZSKHEJKRQTQR5US",
            "HHvrrPZrGdgVbFI+UApMjVNZCJwKiRop57Rw4c+Z"
    );

    private String bucketName = "united-bucket";

    AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();


    public String getBuckets() {
        List<Bucket> buckets = s3client.listBuckets();
        StringBuilder s3 = new StringBuilder();
        for (Bucket bucket : buckets) {
            s3.append(bucket.getName()).append(" ");
        }

        return s3.toString();
    }

    public String getFile() throws IOException {
        S3Object s3object = s3client.getObject(bucketName, "united.csv");
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, new File("/Users/armandgaillard/united.csv"));
        return inputStream.toString();
    }
}
