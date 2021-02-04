package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.userservice.dto.UserAccDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IPatientService;
import com.pharmacy.cpis.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private IUserService userAccService;

    @Autowired
    private IPatientService patientService;

    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAccDTO>> getAllUsers() {

        List<UserAccount> usersACC = userAccService.findAll();

        // convert users to DTOs
        List<UserAccDTO> usersAccDTO = new ArrayList<>();
        for (UserAccount u : usersACC) {
            usersAccDTO.add(new UserAccDTO(u));
        }

        return new ResponseEntity<>(usersAccDTO, HttpStatus.OK);
    }

    @GetMapping(value = "patients")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<Patient>> getAllPatient() {

        List<Patient> patients = patientService.findAllPatient();

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


}
