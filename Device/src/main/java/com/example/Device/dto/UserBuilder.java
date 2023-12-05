package com.example.Device.dto;

import com.example.Device.model.User;

public class UserBuilder {
    private UserBuilder () {

    }

    public static UserDTO userToUserDTO(User user){
        return new UserDTO(user.getId(), user.getName(), user.getRole(), user.getEmail(), user.getPassword());
    }

    public static User UserDTOToUser (UserDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getRole(), userDTO.getEmail(), userDTO.getPassword());
    }
}
