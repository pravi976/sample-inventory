package com.fedex.sample.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.fedex.sample.inventory", "com.fedex.cdc"})
public class SampleInventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleInventoryApplication.class, args);
    }
}