package com.fintrack.notification.service;


import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
@Slf4j
public class SnsNotificationService {

    private final SnsClient snsClient;

    @Value("${aws.sns.topicArn}")
    private String topicArn;

    public SnsNotificationService(
            @Value("${aws.accessKeyId}") String accesKeyId,
            @Value("${aws.secretKey}") String secretKey,
            @Value("${aws.region}") String region) {

        this.snsClient = SnsClient.builder().
                        region(Region.of(region))
                        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accesKeyId,secretKey)))
                        .build();

    }

    public void sendNotification(String message){

        try{
            PublishRequest request = PublishRequest.builder().topicArn(topicArn).message(message).subject("Fintrack transaction alert").build();

            PublishResponse response = snsClient.publish(request);
            log.info("SNS notification sent. MessageId: {}" + response.messageId());
        }catch (Exception e){
            log.error("SNS notification failed. {} " + e.getMessage());
        }
    }
}
