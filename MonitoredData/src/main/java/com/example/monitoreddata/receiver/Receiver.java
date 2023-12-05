package com.example.monitoreddata.receiver;

import com.example.monitoreddata.dto.DeviceDTO;
import com.example.monitoreddata.dto.MonitoredDataDTO;
import com.example.monitoreddata.entities.Device;
import com.example.monitoreddata.entities.MonitoredData;
import com.example.monitoreddata.repositories.DeviceRepository;
import com.example.monitoreddata.service.DeviceService;
import com.example.monitoreddata.service.HourlyConsumptionService;
import com.example.monitoreddata.service.MonitoredDataService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class Receiver {
    private final String QUEUE_NAME = "date";
    @Autowired
    private final MonitoredDataService monitoredDataService;
    private final HourlyConsumptionService hourlyConsumptionService;
    private final DeviceService deviceService;

    @Autowired
    public Receiver(MonitoredDataService monitoredDataService, HourlyConsumptionService hourlyConsumptionService,DeviceService deviceService){

        this.monitoredDataService = monitoredDataService;
        this.hourlyConsumptionService = hourlyConsumptionService;
        this.deviceService=deviceService;
    }

    public void receiverMethod() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://xhtjccev:l9ou5M_FGBNzSjV2hraOE0OpMMlrwd_D@moose.rmq.cloudamqp.com/xhtjccev");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            JSONObject jsonMessage = new JSONObject(message);

            if (jsonMessage.has("device_id") && jsonMessage.has("max_energy_consumption")) {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setIdDevice(jsonMessage.getInt("device_id"));
                deviceDTO.setMaxEnergyConsumption((float) jsonMessage.getDouble("max_energy_consumption"));
                deviceService.createDevice(deviceDTO);
                System.out.println(" [x] Received DeviceDTO: " + message);
            } else if (jsonMessage.has("measurement_value") && jsonMessage.has("device_id") && jsonMessage.has("timestamp")) {
                MonitoredDataDTO data = new MonitoredDataDTO();
                data.setEnergyConsumption((float) jsonMessage.getDouble("measurement_value"));
                data.setIdDevice(jsonMessage.getInt("device_id"));
                LocalDateTime timestamp = LocalDateTime.parse(jsonMessage.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                data.setTimeStamp(timestamp);
                monitoredDataService.addMonitoredData(data);
                System.out.println(" [x] Received MonitoredDataDTO: " + message);
            } else {
                System.out.println(" [!] Unknown message format: " + message);
            }
            List<MonitoredData> allMonitoredData = monitoredDataService.getAllMonitoredData();
            hourlyConsumptionService.calculateHourlyConsumption(allMonitoredData);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}
