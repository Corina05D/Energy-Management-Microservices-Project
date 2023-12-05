package com.example.Device.controller;

import com.example.Device.dto.DeviceDTO;
import com.example.Device.dto.LoginDTO;
import com.example.Device.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/User")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/IsUserVerified")
    public boolean isUserVerified(@Valid @RequestBody LoginDTO loginDTO){
        return this.userService.verifyUser(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @PostMapping("/UserRole/{email}")
    public String UserRole(@PathVariable("email") String email){
        if(!userService.userRole(email).equals("error")){
            return userService.userRole(email);
        }
        return "";
    }

    @PostMapping("/GetUserId")
    public int GetUserId(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.getUserId(loginDTO);
    }

    @GetMapping("/IsUserAdmin/{userId}")
    public boolean IsUserAdmin(@PathVariable("userId") int userId){
        return userService.userRoleById(userId).equals("administrator");
    }

    @GetMapping("/IsUserSimpleUser/{userId}")
    public boolean IsUserSimpleUser(@PathVariable("userId") int userId){
        return userService.userRoleById(userId).equals("user");
    }

    @GetMapping("/GetDevices/{userId}")
    public List<DeviceDTO> GetDevicesForUser(@PathVariable("userId") int userId) {
        return userService.getDevices(userId);
    }
}
