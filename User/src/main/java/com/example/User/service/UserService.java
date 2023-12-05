package com.example.User.service;

import com.example.User.dto.LoginDTO;
import com.example.User.model.User;
import com.example.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
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

}