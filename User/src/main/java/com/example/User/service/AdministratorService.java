package com.example.User.service;

import com.example.User.dto.UserBuilder;
import com.example.User.dto.UserDTO;
import com.example.User.model.User;
import com.example.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdministratorService {
    private final UserRepository userRepository;

    @Autowired
    public AdministratorService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public List<UserDTO> getUsers () {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserBuilder::userToUserDTO)
                .collect(Collectors.toList());
    }

    public void create(User user){
        User u=userRepository.save(user);
    }

    public boolean tryDelete(int id)
    {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
        {
            User u=user.get();
            userRepository.delete(u);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void update(UserDTO userDTO) {
        Optional<User> user=userRepository.findById(userDTO.getId());
        if (user.isPresent()){
            userRepository.delete(user.get());
            userRepository.save(UserBuilder.UserDTOToUser(userDTO));
        }
    }

    public UserDTO getByEmail(String email){
        List<User> users = userRepository.findAll();
        return UserBuilder.userToUserDTO(users.stream().filter(user -> user.getEmail().equals(email)).findFirst().get());
    }

    public UserDTO getCurrentUser(int idUser){
        return UserBuilder.userToUserDTO(this.userRepository.findById(idUser).get());
    }
}
