package com.example.monitoreddata.repositories;

import com.example.monitoreddata.entities.Device;
import com.example.monitoreddata.entities.MonitoredData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
