package com.example.User.controller;

import com.example.User.dto.UserDTO;
import com.example.User.model.User;
import com.example.User.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("AddUser")
    public boolean insertUser(@Valid @RequestBody User user) {
        administratorService.create(user);
        return true;
    }

    @DeleteMapping(value = "DeleteUser/{id}")
    public boolean deleteUser(@PathVariable("id") int userId){
        administratorService.tryDelete(userId);
        return true;
    }

    @GetMapping(value = "GetByEmail/{email}")
    public ResponseEntity<UserDTO> getPerson(@PathVariable("email") String email) {
        UserDTO dto = administratorService.getByEmail(email);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("UpdateUser")
    public boolean updateUser(@Valid @RequestBody UserDTO user) {
        administratorService.update(user);
        return true;
    }

    @GetMapping(value = "GetCurrentUser/{idUser}")
    public UserDTO getCurrentUser(@PathVariable("idUser") int idUser) {
        return administratorService.getCurrentUser(idUser);
    }


}
