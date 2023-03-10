package com.huashijun.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.huashijun"})
public class FaceRecognitionApplication {

    public static void main(String[] args) {

        SpringApplication.run(FaceRecognitionApplication.class, args);
    }

}
