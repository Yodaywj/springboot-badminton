package com.ywj.badminton;

import com.ywj.badminton.test.RabbitAmqpTutorialsRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ywj.badminton.mapper")
@EnableScheduling
public class BadmintonApplication {
    public static void main(String[] args) {
        SpringApplication.run(BadmintonApplication.class, args);
    }

}
