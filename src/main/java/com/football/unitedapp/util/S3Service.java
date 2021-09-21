package com.football.unitedapp.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.football.unitedapp.repository.TeamEntity;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class S3Service {

    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIA2ZSKHEJKRQTQR5US",
            "HHvrrPZrGdgVbFI+UApMjVNZCJwKiRop57Rw4c+Z"
    );

    private static final String bucketName = "united-bucket";
    private static final String pathName = "/Users/armandgaillard/united.csv";
    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";

    public AmazonS3 s3client = AmazonS3ClientBuilder
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

    public String downloadFile() throws IOException {
        S3Object s3object = s3client.getObject(bucketName, "united.csv");
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, new File(pathName));

        return "File Downloaded to system";
    }

    public List getFileDetails() throws IOException {
    //    beans.forEach(System.out::println);

        return new CsvToBeanBuilder(new FileReader(getFile()))
                .withType(TeamEntity.class)
                .build()
                .parse();
    }

    private File getFile() throws IOException {
        S3Object s3object = s3client.getObject(bucketName, "united.csv");
        S3ObjectInputStream inputStream = s3object.getObjectContent();

        final File tempFile = File.createTempFile(PREFIX, SUFFIX);

        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(inputStream, out);
        }
        return tempFile;
    }

//    public InputStream getFileInputStreamFromBucket(String fileName) {
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
//        S3Object s3Object = s3client.getObject(getObjectRequest);
//        InputStream fileInputStream = s3Object.getObjectContent();
//        System.out.println("File Input Stream fetched from s3 bucket for File {} ");
//        return fileInputStream;
//    }
//
//    public void test() throws FileNotFoundException {
//        File initialFile = new File("src/main/resources/sample.txt");
//        InputStream targetStream = new FileInputStream(initialFile);
//
//        File csv = File(in)
//    }

}
