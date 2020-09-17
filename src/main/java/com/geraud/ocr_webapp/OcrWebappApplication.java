package com.geraud.ocr_webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OcrWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcrWebappApplication.class, args);
    }

}
