package com.example.monitoreddata.dto;

import com.example.monitoreddata.entities.MonitoredData;

public class MonitoredDataBuilder {

    public MonitoredDataBuilder () {

    }

    public static MonitoredDataDTO monitoredDataToDTO(MonitoredData monitoredData){
        return new MonitoredDataDTO(monitoredData.getId(), monitoredData.getTimeStamp(), monitoredData.getEnergyConsumption(), monitoredData.getIdDevice());
    }

    public static MonitoredData DTOToMonitoredData (MonitoredDataDTO monitoredDataDTO) {
        return new MonitoredData(monitoredDataDTO.getTimeStamp(), monitoredDataDTO.getEnergyConsumption(), monitoredDataDTO.getIdDevice());
    }
}
