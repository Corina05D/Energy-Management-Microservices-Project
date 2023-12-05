package com.example.Device.service;

import com.example.Device.dto.DeviceBuilder;
import com.example.Device.dto.DeviceDTO;
import com.example.Device.dto.LoginDTO;
import com.example.Device.model.User;
import com.example.Device.model.UserDevice;
import com.example.Device.repository.DeviceRepository;
import com.example.Device.repository.UserDeviceRepository;
import com.example.Device.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private  final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserDeviceRepository userDeviceRepository;

    @Autowired
    public UserService(UserRepository userRepository,DeviceRepository deviceRepository,UserDeviceRepository userDeviceRepository){
        this.userRepository=userRepository;
        this.deviceRepository=deviceRepository;
        this.userDeviceRepository=userDeviceRepository;
    }

    public boolean verifyUser(String email, String password)
    {
        User user=userRepository.findByEmail(email);
        if(user!=null){
            if(user.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public String userRole(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return user.getRole();
        }
        return "error";
    }

    public String userRoleById(int id){
        Optional<User> user=userRepository.findById(id);
        if(user.isPresent()){
            if(user.get().getRole().equals("administrator"))
                return "administrator";
            else return "user";
        }
        return "error";
    }

    public int getUserId(LoginDTO loginDTO){
        User user=userRepository.findByEmail(loginDTO.getEmail());
        if(user!=null){
            if(user.getPassword().equals(loginDTO.getPassword()))
                return user.getId();
        }
        return 0;
    }

    public List<DeviceDTO> getDevices(int idUser){
        List<Integer> devicesIds = userDeviceRepository.findAll().stream().filter(userDevice -> userDevice.getIdUser() == idUser).map(UserDevice::getIdDevice).toList();
        return deviceRepository.findAll().stream()
                .filter(device -> devicesIds.contains(device.getId())).map(DeviceBuilder::deviceToDeviceDTO).collect(Collectors.toList());
    }


}
