package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.Person;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.*;
import com.pharmacy.cpis.userservice.dto.JwtAuthenticationRequest;
import com.pharmacy.cpis.userservice.dto.UserActivationDTO;
import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.dto.UserTokenState;
import com.pharmacy.cpis.util.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

// Controller in charge of user authentication
@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    @Autowired
    private Environment env;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IAuthorityService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // The first endpoint that affects the user when logging in.
    // Then he only knows his username and password and forwards it to the backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        // Insert the user into the current security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create a token for that user
        UserAccount user = (UserAccount) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername(),userService.getUserRole(user) );
        int expiresIn = tokenUtils.getExpiredIn();

        // Return the token in response to successful authentication
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    // Endpoint to register a new user
    @PostMapping("/signup")
    public ResponseEntity<UserAccount> addUser(@RequestBody UserRegisterDTO userRequest) {

        UserAccount existUser = userService.findByEmail(userRequest.getEmail());
        if (existUser != null) {
            System.out.println("\n\nUser with that email, already exists\n\n");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Patient addedPatient = addNewPatient(userRequest);
        UserAccount addedAccount = addNewAccount(userRequest, addedPatient);
        sendActivationEmail(addedAccount);

        return new ResponseEntity<>(addedAccount, HttpStatus.CREATED);
    }

    // GET is because of easy click-activate method
    @GetMapping("activate/{id}")
    public void activateAccount(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        userService.activateUserAccount(id);
        redirectToLoginPage(httpServletResponse);
    }

    private void redirectToLoginPage(HttpServletResponse httpServletResponse) {
        String projectUrl = env.getProperty("APP_FRONT") + env.getProperty("FRONT_PORT") + "/";
        httpServletResponse.setHeader("Location", projectUrl);
        httpServletResponse.setStatus(302);
    }

    private void sendActivationEmail(UserAccount addedAccount) {
        UserActivationDTO userActivationDTO = new UserActivationDTO();
        userActivationDTO.setId(addedAccount.getId().toString());
        userActivationDTO.setEmail(addedAccount.getEmail());
        userActivationDTO.setName(addedAccount.getPerson().getName());

        // email sending
        try {
            System.out.println("Sending mail in process ..");
            emailService.sendActivationEmailAsync(userActivationDTO);
        }catch( Exception e ){
            System.out.println("Error during sending email: " + e.getMessage());
        }
    }

    private UserAccount addNewAccount(UserRegisterDTO userRequest, Patient addedPatient) {
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setEmail(userRequest.getEmail());
        newUserAccount.setPassword( passwordEncoder.encode(userRequest.getPassword()));
        newUserAccount.setActive(false);
        List<Authority> auth = authService.findByName("ROLE_PATIENT");
        newUserAccount.setAuthorities(auth);
        newUserAccount.setPerson(addedPatient);

        UserAccount addedAccount = userService.save(newUserAccount);
        return addedAccount;
    }

    private Patient addNewPatient(UserRegisterDTO userRequest) {
        Patient newPatient = new Patient();
        newPatient.setAddress(userRequest.getAddress());
        newPatient.setCity(userRequest.getCity());
        newPatient.setCountry(userRequest.getCountry());
        newPatient.setName(userRequest.getName());
        newPatient.setSurname(userRequest.getSurname());
        newPatient.setPhoneNumber(userRequest.getMobile());
        Patient addedPatient = patientService.save(newPatient);
        return addedPatient;
    }

}
