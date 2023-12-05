package com.example.monitoreddata;

import com.example.monitoreddata.receiver.Receiver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MonitoredDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoredDataApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(Receiver receiver) {
        return args -> {
            new Thread(() -> {
                try {
                    receiver.receiverMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        };
    }
}
