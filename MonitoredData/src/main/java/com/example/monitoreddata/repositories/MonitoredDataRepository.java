package com.example.monitoreddata.repositories;

import com.example.monitoreddata.entities.MonitoredData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredDataRepository extends JpaRepository<MonitoredData, Integer> {
}
