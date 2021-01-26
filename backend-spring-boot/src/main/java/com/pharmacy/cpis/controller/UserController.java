package com.pharmacy.cpis.controller;

import com.pharmacy.cpis.dto.UserAccDTO;
import com.pharmacy.cpis.model.UserAcc;
import com.pharmacy.cpis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userAccService;

    @GetMapping
    public ResponseEntity<List<UserAccDTO>> getUsersAccs() {

        List<UserAcc> usersACC = userAccService.findAll();

        // convert users to DTOs
        List<UserAccDTO> usersAccDTO = new ArrayList<>();
        for (UserAcc u : usersACC) {
            usersAccDTO.add(new UserAccDTO(u));
        }

        return new ResponseEntity<>(usersAccDTO, HttpStatus.OK);
    }

    @GetMapping("userAcc/{id}")
    public ResponseEntity<UserAccDTO> getUserAcc(@PathVariable Long id) {

        UserAcc userAcc = userAccService.findOne(id);

        if (userAcc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserAccDTO> saveUserAcc(@RequestBody UserAcc userAcc) {

        UserAcc userAccForSave = new UserAcc();
        userAccForSave.setEmail(userAcc.getEmail());
        userAccForSave.setPassword(userAcc.getPassword());
        userAccForSave.setActive(true);

        userAcc = userAccService.save(userAccForSave);
        return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.CREATED);
    }
}
