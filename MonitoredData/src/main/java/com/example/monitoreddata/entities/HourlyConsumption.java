package com.example.monitoreddata.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hourly_consumption")
public class HourlyConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int deviceId;
    private LocalDateTime hour;
    private float consumption;

    public HourlyConsumption() {
    }

    public HourlyConsumption(int deviceId, LocalDateTime hour, float consumption) {
        this.deviceId = deviceId;
        this.hour = hour;
        this.consumption = consumption;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }
}
