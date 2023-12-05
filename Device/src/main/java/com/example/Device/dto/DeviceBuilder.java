package com.example.Device.dto;

import com.example.Device.model.Device;
import com.example.Device.repository.DeviceRepository;

public class DeviceBuilder {
    private DeviceBuilder () {
    }
    public static DeviceDTO deviceToDeviceDTO(Device device){
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMaximumEnergyConsumption());
    }

    public static Device DeviceDTOToDevice (DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getDescription(), deviceDTO.getAddress(), deviceDTO.getMaximumEnergyConsumption());
    }
}
