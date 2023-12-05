package com.example.monitoreddata.service;

import com.example.monitoreddata.entities.HourlyConsumption;
import com.example.monitoreddata.entities.MonitoredData;
import com.example.monitoreddata.repositories.DeviceRepository;
import com.example.monitoreddata.repositories.HourlyConsumptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HourlyConsumptionService {

    private final HourlyConsumptionRepository hourlyConsumptionRepository;
    private final DeviceRepository deviceRepository;

    public HourlyConsumptionService(HourlyConsumptionRepository hourlyConsumptionRepository, DeviceRepository deviceRepository) {
        this.hourlyConsumptionRepository = hourlyConsumptionRepository;
        this.deviceRepository=deviceRepository;
    }

    public void calculateHourlyConsumption(List<MonitoredData> monitoredDataList) {
        // Sortăm lista de MonitoredData după timestamp
        monitoredDataList.sort(Comparator.comparing(MonitoredData::getTimeStamp));

        // Map pentru a stoca ultima valoare citită într-o oră pentru fiecare dispozitiv
        Map<Integer, Map<LocalDateTime, Float>> lastValueMap = new HashMap<>();

        // Iterăm prin lista sortată de MonitoredData
        for (MonitoredData monitoredData : monitoredDataList) {
            int deviceId = monitoredData.getIdDevice();
            LocalDateTime timeStamp = monitoredData.getTimeStamp();
            Float energyConsumption = monitoredData.getEnergyConsumption();

            // Initializăm map-ul pentru dispozitivul curent dacă nu există
            lastValueMap.putIfAbsent(deviceId, new HashMap<>());

            // Calculăm ora la care s-a efectuat măsurătoarea, ignorând minutele și secundele
            LocalDateTime hourKey = timeStamp.withMinute(0).withSecond(0).withNano(0);

            // Adăugăm ultima valoare citită la oră în map
            lastValueMap.get(deviceId).put(hourKey, energyConsumption);
        }

        // Map pentru a stoca prima valoare citită într-o oră pentru fiecare dispozitiv
        Map<Integer, Map<LocalDateTime, Float>> firstValueMap = new HashMap<>();

        // Iterăm prin map-ul cu ultima valoare și inversăm ordinea pentru a obține prima valoare
        for (Map.Entry<Integer, Map<LocalDateTime, Float>> entry : lastValueMap.entrySet()) {
            int deviceId = entry.getKey();
            Map<LocalDateTime, Float> lastValues = entry.getValue();

            // Initializăm map-ul pentru dispozitivul curent
            firstValueMap.putIfAbsent(deviceId, new HashMap<>());

            // Iterăm prin ultimele valori și obținem prima valoare citită în oră
            lastValues.forEach((hour, value) -> {
                Float firstValue = getFirstValueInHour(monitoredDataList, deviceId, hour);
                firstValueMap.get(deviceId).put(hour, firstValue);
            });
        }

        // Iterăm prin map și calculăm diferența pentru fiecare oră și dispozitiv
        for (Map.Entry<Integer, Map<LocalDateTime, Float>> entry : lastValueMap.entrySet()) {
            int deviceId = entry.getKey();
            Map<LocalDateTime, Float> lastValues = entry.getValue();
            Map<LocalDateTime, Float> firstValues = firstValueMap.get(deviceId);

            for (Map.Entry<LocalDateTime, Float> lastValueEntry : lastValues.entrySet()) {
                LocalDateTime hour = lastValueEntry.getKey();
                Float lastValue = lastValueEntry.getValue();
                Float firstValue = firstValues.get(hour);

                // Verificăm dacă există deja o înregistrare pentru această oră și acest dispozitiv
                HourlyConsumption existingEntry = hourlyConsumptionRepository.findByDeviceIdAndHour(deviceId, hour);

                if (existingEntry != null) {
                    // Dacă există, actualizăm consumul
                    existingEntry.setConsumption((lastValue - firstValue));
                    hourlyConsumptionRepository.save(existingEntry);
                } else {
                    // Dacă nu există, creăm o nouă înregistrare
                    HourlyConsumption newEntry = new HourlyConsumption();
                    newEntry.setDeviceId(deviceId);
                    newEntry.setHour(hour);
                    newEntry.setConsumption((lastValue - firstValue));
                    hourlyConsumptionRepository.save(newEntry);
                }
            }
        }
    }

    // Metoda pentru a obține prima valoare dintr-o anumită oră și pentru un anumit dispozitiv
    private Float getFirstValueInHour(List<MonitoredData> monitoredDataList, int deviceId, LocalDateTime hour) {
        // Filtrăm valorile pentru dispozitivul și ora specificate și obținem prima valoare
        return monitoredDataList.stream()
                .filter(data -> data.getIdDevice() == deviceId && data.getTimeStamp().equals(hour))
                .findFirst()
                .map(MonitoredData::getEnergyConsumption)
                .orElse(0.0f);
    }


    public List<String> getNotificationForHourlyConsumption(int deviceId) {
        List<String> notifications = new ArrayList<>();
        List<HourlyConsumption> hourlyConsumptions = hourlyConsumptionRepository.findByDeviceId(deviceId);
        Float deviceMaxEnergyConsumption = deviceRepository.findById(deviceId).orElseThrow().getMaxEnergyConsumption();

        for (HourlyConsumption hourlyConsumption : hourlyConsumptions) {
            String notification;
            if (hourlyConsumption.getConsumption() <= deviceMaxEnergyConsumption) {
                notification = "The current energy consumption did not exceed the maximum value. Current value: " +
                        hourlyConsumption.getConsumption() +
                        ", Maximum value: " + deviceMaxEnergyConsumption;
            } else {
                notification = "The current energy consumption EXCEEDED the maximum value. Current value: " +
                        hourlyConsumption.getConsumption() +
                        ", Maximum value: " + deviceMaxEnergyConsumption;
            }
            notifications.add(notification);
        }

        return notifications;
    }
}
