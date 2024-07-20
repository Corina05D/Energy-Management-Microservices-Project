package com.example.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

@Component
public class MessageProducer {

    private static final String QUEUE_NAME = "date";
    private volatile boolean isRunning = true;
    private String deviceId;

    public void stopProducing() {
        isRunning = false;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public void produceMessage() throws InterruptedException, JSONException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        String file = "sensor.csv";
        String line;
        Properties prop = new Properties();

        while (isRunning) {
            // Daca nu ai device_id, asteapta pana cand este setat de utilizator
            while (isRunning && (deviceId == null || deviceId.isEmpty())) {
                Thread.sleep(2000); // Asteapta 2 secunde si verifica din nou
            }

            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqps://xhtjccev:l9ou5M_FGBNzSjV2hraOE0OpMMlrwd_D@moose.rmq.cloudamqp.com/xhtjccev");

            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    while ((line = br.readLine()) != null && isRunning) {
                        JSONObject message = new JSONObject();
                        message.put("timestamp", LocalDateTime.now());
                        message.put("device_id", deviceId);
                        message.put("measurement_value", line);

                        String messageToBeSent = message.toString();
                        channel.basicPublish("", QUEUE_NAME, null, messageToBeSent.getBytes(StandardCharsets.UTF_8));
                        System.out.println(" [x] Sent '" + message + "'");

                        // Așteaptă 10 secunde înainte de a citi următoarea linie
                        Thread.sleep(10000);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}