package com.football.unitedapp.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class s3Service {

    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIA2ZSKHEJKRQTQR5US",
            "HHvrrPZrGdgVbFI+UApMjVNZCJwKiRop57Rw4c+Z"
    );

    AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_2)
            .build();

    List<Bucket> buckets = s3client.listBuckets();
    System.out.println(buckets.getName());
    System.o

//    for
//    for (Bucket bucket : buckets) {
//        System.out.println(bucket.getName());
//    }


}
