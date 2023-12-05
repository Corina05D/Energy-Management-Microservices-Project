package com.example.Device.dto;

import com.example.Device.model.User;

public class DeviceDTO {
    private int id;
    private String description;
    private String address;
    private Float maximumEnergyConsumption;

    public DeviceDTO() {
    }

    public DeviceDTO(int id, String description, String address, Float maximumEnergyConsumption) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maximumEnergyConsumption = maximumEnergyConsumption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getMaximumEnergyConsumption() {
        return maximumEnergyConsumption;
    }

    public void setMaximumEnergyConsumption(Float maximumEnergyConsumption) {
        this.maximumEnergyConsumption = maximumEnergyConsumption;
    }
}
