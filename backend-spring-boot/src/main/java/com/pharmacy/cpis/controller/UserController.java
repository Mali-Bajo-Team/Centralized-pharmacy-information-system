package com.pharmacy.cpis.controller;

import com.pharmacy.cpis.dto.UserDTO;
import com.pharmacy.cpis.model.UserAcc;
import com.pharmacy.cpis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getCourses() {

        List<UserAcc> users = userService.findAll();

        // convert users to DTOs
        List<UserDTO> usersDTO = new ArrayList<>();
        for (UserAcc u : users) {
            usersDTO.add(new UserDTO(u));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }
}
