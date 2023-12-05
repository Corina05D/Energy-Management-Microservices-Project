package com.example.monitoreddata.controller;


import com.example.monitoreddata.dto.MonitoredDataDTO;
import com.example.monitoreddata.service.HourlyConsumptionService;
import com.example.monitoreddata.service.MonitoredDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class MonitoredDataController {
    private final MonitoredDataService monitoredDataService;
    private final HourlyConsumptionService hourlyConsumptionService;

    @Autowired
    public MonitoredDataController(MonitoredDataService monitoredDataService,HourlyConsumptionService hourlyConsumptionService) {
        this.monitoredDataService = monitoredDataService;
        this.hourlyConsumptionService=hourlyConsumptionService;
    }

    @GetMapping("GetMonitoredData/{deviceId}")
    public List<MonitoredDataDTO> GetMonitoredData(@PathVariable("deviceId") int deviceId) {
        return monitoredDataService.getMonitoredData(deviceId);
    }

    @GetMapping("GetNotification/{deviceId}")
    public List<String> GetNotification(@PathVariable("deviceId") int deviceId) {
        return hourlyConsumptionService.getNotificationForHourlyConsumption(deviceId);
    }
}
