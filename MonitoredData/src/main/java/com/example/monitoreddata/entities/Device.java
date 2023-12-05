package com.example.monitoreddata.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "device")
public class Device{
    @Id
    private int idDevice;
    private Float maxEnergyConsumption;

    public Device() {
    }

    public Device(int idDevice,Float energyConsumption) {
        this.maxEnergyConsumption = energyConsumption;
        this.idDevice = idDevice;
    }


    public Float getMaxEnergyConsumption() {
        return maxEnergyConsumption;
    }

    public void setMaxEnergyConsumption(Float energyConsumption) {
        this.maxEnergyConsumption = energyConsumption;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }
}
