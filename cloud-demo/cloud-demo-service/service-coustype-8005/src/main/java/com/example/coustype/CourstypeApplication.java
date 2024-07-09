package com.example.coustype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CourstypeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourstypeApplication.class, args);
    }
}