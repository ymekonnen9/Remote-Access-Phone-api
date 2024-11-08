package com.remoteAccess.backend.AwsConfig;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    // Injecting AWS credentials from application.properties
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String accessSecret;

    @Value("${cloud.aws.region.static}")
    private String region;

    // Injecting the S3 bucket name
    @Value("${s3.bucket.name}")
    private String bucketName;

    // Creating a bean for Amazon S3 client
    @Bean
    public AmazonS3 s3Client() {
        // Creating AWS credentials using access key and secret key
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);

        // Building the Amazon S3 client with the specified credentials and region
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();

        // Optionally, use the bucket name here if needed for certain operations
        // For example, checking if the bucket exists:
        if (!s3Client.doesBucketExistV2(bucketName)) {
            System.out.println("Bucket does not exist: " + bucketName);
        }

        return s3Client;
    }
}
