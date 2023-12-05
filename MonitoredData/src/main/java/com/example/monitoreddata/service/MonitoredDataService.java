package com.example.monitoreddata.service;

import com.example.monitoreddata.dto.MonitoredDataBuilder;
import com.example.monitoreddata.dto.MonitoredDataDTO;
import com.example.monitoreddata.entities.MonitoredData;
import com.example.monitoreddata.repositories.MonitoredDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonitoredDataService {
    private final MonitoredDataRepository monitoredDataRepository;

    @Autowired
    public MonitoredDataService(MonitoredDataRepository monitoredDataRepository) {
        this.monitoredDataRepository = monitoredDataRepository;
    }

    public List<MonitoredDataDTO> getMonitoredData(int idDevice){
        return monitoredDataRepository.findAll().stream().filter(monitoredData -> monitoredData.getIdDevice() == idDevice).map(MonitoredDataBuilder::monitoredDataToDTO).collect(Collectors.toList());
    }

    public void addMonitoredData(MonitoredDataDTO monitoredDataDTO){
        MonitoredData data = MonitoredDataBuilder.DTOToMonitoredData(monitoredDataDTO);
        monitoredDataRepository.save(data);
    }

    public List<MonitoredData> getAllMonitoredData() {
        return monitoredDataRepository.findAll();
    }

}
