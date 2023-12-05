package com.example.monitoreddata.dto;

import java.time.LocalDateTime;

public class MonitoredDataDTO {
    private int id;
    private LocalDateTime timeStamp;
    private Float energyConsumption;
    private int idDevice;

    public MonitoredDataDTO() {
    }

    public MonitoredDataDTO(int id, LocalDateTime timeStamp, Float energyConsumption, int idDevice) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.energyConsumption = energyConsumption;
        this.idDevice = idDevice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Float getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Float energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

}
