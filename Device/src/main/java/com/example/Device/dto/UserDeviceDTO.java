package com.example.Device.dto;

public class UserDeviceDTO {
    private int id;
    private int idUser;
    private int idDevice;

    public UserDeviceDTO() {
    }

    public UserDeviceDTO(int id, int idUser, int idDevice) {
        this.id = id;
        this.idUser = idUser;
        this.idDevice = idDevice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

}
