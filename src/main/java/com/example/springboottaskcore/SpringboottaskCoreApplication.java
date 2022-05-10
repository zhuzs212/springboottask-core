package com.example.springboottaskcore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.springboottaskcore.mapper")
@SpringBootApplication(scanBasePackages = {"com.example.springboottaskcore"})
public class SpringboottaskCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringboottaskCoreApplication.class, args);
    }

}

