package com.example.monitoreddata.repositories;

import com.example.monitoreddata.entities.HourlyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HourlyConsumptionRepository extends JpaRepository<HourlyConsumption, Integer> {
    HourlyConsumption findByDeviceIdAndHour(int deviceId, LocalDateTime hour);
    List<HourlyConsumption> findByDeviceId(int deviceId);
}
