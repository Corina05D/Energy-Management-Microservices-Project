package com.example.Device.repository;

import com.example.Device.model.User;
import com.example.Device.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {
    public UserDevice findByIdDevice(int idDevice);
    public UserDevice findByIdUser(int idUser);
}
