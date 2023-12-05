package com.example.Device.dto;


import com.example.Device.model.UserDevice;

public class UserDeviceBuilder {
    private UserDeviceBuilder () {

    }

    public static UserDeviceDTO userDeviceToDTO(UserDevice userDevice){
        return new UserDeviceDTO(userDevice.getId(), userDevice.getIdUser(), userDevice.getIdDevice());
    }

    public static UserDevice DTOToUserDevice (UserDeviceDTO userDeviceDTO) {
        return new UserDevice(userDeviceDTO.getIdUser(), userDeviceDTO.getIdDevice());
    }
}
