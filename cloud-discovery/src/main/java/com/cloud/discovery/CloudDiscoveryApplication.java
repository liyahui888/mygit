package com.cloud.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CloudDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDiscoveryApplication.class, args);
    }

}
