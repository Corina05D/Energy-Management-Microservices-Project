package com.example.User.dto;

import com.example.User.model.User;

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
