package com.pharmacy.cpis.controller;

import com.pharmacy.cpis.dto.UserAccDTO;
import com.pharmacy.cpis.dto.UserActivationDTO;
import com.pharmacy.cpis.dto.UserRegisterDTO;
import com.pharmacy.cpis.model.UserAcc;
import com.pharmacy.cpis.service.EmailService;
import com.pharmacy.cpis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    // Use a class to read values from the application.properties file
    @Autowired
    private Environment env;

    @Autowired
    private UserService userAccService;

    @Autowired
    private EmailService emailService;

    @CrossOrigin
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

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<UserAcc> addUser(@RequestBody UserRegisterDTO userRequest) {

        // TODO: When we merge with security branch, make this exception & make better signup !! For now, it's harder, i will make just more conflicts...
//        UserAcc existUser = userAccService.findByUsername(userRequest.getUsername());
//        if (existUser != null) {
//            System.out.println("User with that email, already exists");
//            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
//        }

        UserAcc newUser = new UserAcc();

        newUser.setActive(false);
        newUser.setPassword(userRequest.getPassword());
        newUser.setEmail(userRequest.getEmail());

        UserAcc user = userAccService.save(newUser);
        UserActivationDTO userActivationDTO = new UserActivationDTO();
        userActivationDTO.setId(user.getId().toString());
        userActivationDTO.setEmail(user.getEmail());
        userActivationDTO.setName("dear user");         // TODO: When merge with security branch, add here real name!

        // email sending
        try {
            System.out.println("Slanje mejla u toku..");
            emailService.sendNotificaitionAsync(userActivationDTO);
        }catch( Exception e ){
            System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserAccDTO> updateUserAcc(@RequestBody UserAcc userAcc) {

        // a userAcc must exist
        UserAcc userAccForUpdate = userAccService.findOne(userAcc.getId());

        if (userAccForUpdate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userAccForUpdate.setEmail(userAcc.getEmail());
        userAccForUpdate.setPassword(userAcc.getPassword());

        userAcc = userAccService.save(userAccForUpdate);
        return new ResponseEntity<>(new UserAccDTO(userAcc), HttpStatus.OK);
    }

    // GET is because of easy click-activate method
    @GetMapping("activate/{id}")
    public void activateAccount(@PathVariable Long id, HttpServletResponse httpServletResponse) {

        // a userAcc must exist
        UserAcc userAccForUpdate = userAccService.findOne(id);

        if (userAccForUpdate == null) {
            return;
        }

        userAccForUpdate.setActive(true);
        userAccService.save(userAccForUpdate);
        redirectToLoginPage(httpServletResponse);
    }

    private void redirectToLoginPage(HttpServletResponse httpServletResponse) {
        String projectUrl = env.getProperty("APP_FRONT") + env.getProperty("FRONT_PORT") + "/";
        httpServletResponse.setHeader("Location", projectUrl);
        httpServletResponse.setStatus(302);
    }


    @DeleteMapping(value = "userAcc/{id}")
    public ResponseEntity<Void> deleteUserAcc(@PathVariable Long id) {

        UserAcc userAcc = userAccService.findOne(id);

        if (userAcc != null) {
            userAccService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/signup/async")
    public String signUpAsync(@RequestBody UserActivationDTO user){

        //slanje emaila
        try {
            System.out.println("Slanje mejla u toku..");
            emailService.sendNotificaitionAsync(user);
        }catch( Exception e ){
            System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
        }

        return "success";
    }

}
