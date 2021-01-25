package com.pharmacy.cpis.controller;

import com.pharmacy.cpis.dto.UserAccDTO;
import com.pharmacy.cpis.model.UserAcc;
import com.pharmacy.cpis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userAccService;

    @GetMapping
    public ResponseEntity<List<UserAccDTO>> getCourses() {

        List<UserAcc> usersACC = userAccService.findAll();

        // convert users to DTOs
        List<UserAccDTO> usersAccDTO = new ArrayList<>();
        for (UserAcc u : usersACC) {
            usersAccDTO.add(new UserAccDTO(u));
        }

        return new ResponseEntity<>(usersAccDTO, HttpStatus.OK);
    }

    @GetMapping("userAcc/{id}")
    public ResponseEntity<UserAccDTO> getCourse(@PathVariable Long id) {

        UserAcc userAcc = userAccService.findOne(id);

        if (userAcc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.OK);
    }
}
