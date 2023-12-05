package com.example.User.validators;

import com.example.User.model.User;
import com.example.User.repository.UserRepository;

public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean CheckIfUserExist(int id) {
        User user = userRepository.findById(id).get();
        if(user != null){
            return true;
        }
        return false;
    }
}
