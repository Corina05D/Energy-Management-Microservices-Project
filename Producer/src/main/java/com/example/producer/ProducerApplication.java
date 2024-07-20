package com.example.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProducerApplication.class, args);

        MessageProducer messageProducer = context.getBean(MessageProducer.class);
        MessageProducerGUI messageProducerGUI = context.getBean(MessageProducerGUI.class);

        // Configurare explicită pentru headless
        System.setProperty("java.awt.headless", "false");

        // Creare și afișare UI
        SwingUtilities.invokeLater(() -> messageProducerGUI.createAndShowUI(messageProducer));
    }

}
