package com.server.kltn.motel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@ComponentScan(basePackages = "com.server.kltn.motel")
public class WebConfig implements WebMvcConfigurer{
	private final long MAX_AGE_SECS = 60;

    @Value("${app.cors.allowedOrigins}")
    private String[] allowedOrigins;
    
    @Value("${aws.accessKey}")
    private String accessKeyS3;

    @Value("${aws.secretKey}")
    private String secretKeyS3;

    @Value("${aws.s3.region}")
    private String region;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	 registry.addMapping("/**")
         .allowedOrigins(allowedOrigins)
         .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
         .maxAge(MAX_AGE_SECS);
    }
    @Bean
    AmazonS3 amazonS3(){
        AWSCredentials awsCredentials= new BasicAWSCredentials(accessKeyS3,secretKeyS3);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
