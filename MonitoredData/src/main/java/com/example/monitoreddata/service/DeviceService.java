package com.example.monitoreddata.service;

import com.example.monitoreddata.dto.DeviceBuilder;
import com.example.monitoreddata.dto.DeviceDTO;
import com.example.monitoreddata.dto.MonitoredDataBuilder;
import com.example.monitoreddata.dto.MonitoredDataDTO;
import com.example.monitoreddata.entities.Device;
import com.example.monitoreddata.entities.MonitoredData;
import com.example.monitoreddata.repositories.DeviceRepository;
import com.example.monitoreddata.repositories.MonitoredDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceService {
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public boolean TryDeleteDevice(int id)
    {
        Device device = deviceRepository.findById(id).get();
        if (device == null)
        {
            return false;
        }
        else
        {
            deviceRepository.delete(device);
            return true;
        }
    }

    public void createDevice(DeviceDTO deviceDTO)
    {
        Device device = DeviceBuilder.DTOToDevice(deviceDTO);
        Optional<Device> d=deviceRepository.findById(device.getIdDevice());
        if(d.isPresent()) {
            TryDeleteDevice(d.get().getIdDevice());
        }
        else { deviceRepository.save(device);}
    }


}
