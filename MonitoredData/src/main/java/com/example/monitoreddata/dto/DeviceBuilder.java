package com.example.monitoreddata.dto;

import com.example.monitoreddata.entities.Device;
import com.example.monitoreddata.entities.MonitoredData;

public class DeviceBuilder {

    public DeviceBuilder () {

    }

    public static DeviceDTO deviceToDTO(Device device){
        return new DeviceDTO(device.getIdDevice(), device.getMaxEnergyConsumption());
    }

    public static Device DTOToDevice (DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getIdDevice(),deviceDTO.getMaxEnergyConsumption());
    }
}