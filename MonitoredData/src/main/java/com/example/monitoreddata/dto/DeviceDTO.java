package com.example.monitoreddata.dto;

import jakarta.persistence.*;

import java.io.Serializable;

public class DeviceDTO{

    private int idDevice;
    private Float maxEnergyConsumption;

    public DeviceDTO(){}

    public DeviceDTO(int idDevice,Float energyConsumption) {
        this.idDevice = idDevice;
        this.maxEnergyConsumption = energyConsumption;
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
