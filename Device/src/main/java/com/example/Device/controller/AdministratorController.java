package com.example.Device.controller;

import com.example.Device.dto.DeviceDTO;
import com.example.Device.dto.UserDTO;
import com.example.Device.dto.UserDeviceDTO;
import com.example.Device.model.User;
import com.example.Device.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/Administrator")
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService){
        this.administratorService = administratorService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = administratorService.getUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/AddUser")
    public ResponseEntity<User> insertUser(@Valid @RequestBody UserDTO userDTO) {
        User i=administratorService.create(userDTO);
        return new ResponseEntity<>(i, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/DeleteUser/{id}")
    public boolean deleteUser(@PathVariable("id") int userId){
        administratorService.tryDelete(userId);
        return true;
    }

    @GetMapping(value = "/GetByEmail/{email}")
    public ResponseEntity<UserDTO> getPerson(@PathVariable("email") String email) {
        UserDTO dto = administratorService.getByEmail(email);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/UpdateUser")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserDTO user) {
        User i=administratorService.update(user);
        return new ResponseEntity<>(i, HttpStatus.OK);
    }

    @PostMapping("/AddDevice")
    public boolean insertDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        administratorService.createDevice(deviceDTO);
        return true;
    }

    @GetMapping("/GetDevices")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = administratorService.getDevices();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping(value = "/DeleteDevice/{id}")
    public boolean deleteDevice(@PathVariable("id") int deviceId){
        administratorService.TryDeleteDevice(deviceId);
        return true;
    }

    @GetMapping(value = "/GetDevicesForUser/{idUser}")
    public List<DeviceDTO> getDevicesForUser(@PathVariable("idUser") int idUser){
        return administratorService.getDevicesForASpecificUser(idUser);
    }

    @GetMapping(value = "/GetDeviceIdByName/{name}")
    public int getDevicesForUser(@PathVariable("name") String name){
        return administratorService.getDeviceIdByName(name);
    }

    @GetMapping(value = "/GetCurrentUser/{idUser}")
    public UserDTO getCurrentUser(@PathVariable("idUser") int idUser) {
        return administratorService.getCurrentUser(idUser);
    }

    @PostMapping("/MapUserDevice")
    public boolean insertMappingUserDevice(@Valid @RequestBody UserDeviceDTO userDeviceDTO) throws IOException {
        administratorService.createMapping(userDeviceDTO);
        return true;
    }


}
