package com.example.Device.service;

import com.example.Device.dto.*;
import com.example.Device.model.Device;
import com.example.Device.model.User;
import com.example.Device.model.UserDevice;
import com.example.Device.repository.DeviceRepository;
import com.example.Device.repository.UserDeviceRepository;
import com.example.Device.repository.UserRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;


@Service
@Transactional
public class AdministratorService {
    private  final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserDeviceRepository userDeviceRepository;

    @Autowired
    public AdministratorService(UserRepository userRepository,DeviceRepository deviceRepository,UserDeviceRepository userDeviceRepository){
        this.userRepository=userRepository;
        this.deviceRepository=deviceRepository;
        this.userDeviceRepository=userDeviceRepository;
    }

    public List<UserDTO> getUsers () {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserBuilder::userToUserDTO)
                .collect(Collectors.toList());
    }
    public User create(UserDTO userDTO){
        User user=UserBuilder.UserDTOToUser(userDTO);
        User u=userRepository.save(user);
        return u;
    }

    public boolean tryDelete(int id)
    {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
        {
            User u=user.get();
            UserDevice ud=userDeviceRepository.findByIdUser(u.getId());
            userRepository.delete(u);
            if(ud!=null) userDeviceRepository.delete(ud);
            return true;
        }
        else
        {
            return false;
        }
    }

    public User update(UserDTO userDTO) {
        Optional<User> user=userRepository.findById(userDTO.getId());
        if (user.isPresent()){
            userRepository.delete(user.get());
            User i=userRepository.save(UserBuilder.UserDTOToUser(userDTO));
            return i;
        }
        return null;
    }

    public UserDTO getByEmail(String email){
        List<User> users = userRepository.findAll();
        return UserBuilder.userToUserDTO(users.stream().filter(user -> user.getEmail().equals(email)).findFirst().get());
    }

    public UserDTO getCurrentUser(int idUser){
        return UserBuilder.userToUserDTO(this.userRepository.findById(idUser).get());
    }

    public void createDevice(DeviceDTO deviceDTO)
    {
        Device device = DeviceBuilder.DeviceDTOToDevice(deviceDTO);
        deviceRepository.save(device);
        try {
            produceMessageForDevice(device.getId(),device.getMaximumEnergyConsumption());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public List<DeviceDTO> getDevices () {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(DeviceBuilder::deviceToDeviceDTO)
                .collect(Collectors.toList());
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
            try {
                produceMessageForDevice(device.getId(),device.getMaximumEnergyConsumption());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            deviceRepository.delete(device);
            UserDevice ud=userDeviceRepository.findByIdDevice(device.getId());
            if(ud!=null) userDeviceRepository.delete(ud);
            return true;
        }
    }

    public List<DeviceDTO> getDevicesForASpecificUser(int idUser){
        List<Integer> userDevicesIds = userDeviceRepository.findAll().stream().map(userdevice -> userdevice.getIdDevice()).collect(Collectors.toList());
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(DeviceBuilder::deviceToDeviceDTO).filter(device -> !userDevicesIds.contains(device.getId()))
                .collect(Collectors.toList());
    }

    public int getDeviceIdByName(String name) {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().filter(device -> device.getDescription().equals(name)).findFirst().get().getId();
    }

    public void createMapping(UserDeviceDTO userDeviceDTO) throws IOException {
        Properties properties = new Properties();
        UserDevice userDevice = UserDeviceBuilder.DTOToUserDevice(userDeviceDTO);
        userDeviceRepository.save(userDevice);

    }

    public void produceMessageForDevice(int deviceId, float maxEnergyConsumption)
            throws InterruptedException, JSONException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        String QUEUE_NAME = "date";
        boolean isRunning = true;
        Properties prop = new Properties();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://xhtjccev:l9ou5M_FGBNzSjV2hraOE0OpMMlrwd_D@moose.rmq.cloudamqp.com/xhtjccev");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // Acesta este obiectul JSON pe care îl trimiți pe coadă
            JSONObject message = new JSONObject();
            message.put("device_id", deviceId);
            message.put("max_energy_consumption", maxEnergyConsumption);

            String messageToBeSent = message.toString();
            channel.basicPublish("", QUEUE_NAME, null, messageToBeSent.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
