package com.example.monitoreddata.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitoredData")
public class MonitoredData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime timeStamp;
    private Float energyConsumption;
    private int idDevice;

    public MonitoredData() {
    }

    public MonitoredData(LocalDateTime timeStamp, Float energyConsumption, int idDevice) {
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

    public void setTimeStamp(Float energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }
}
