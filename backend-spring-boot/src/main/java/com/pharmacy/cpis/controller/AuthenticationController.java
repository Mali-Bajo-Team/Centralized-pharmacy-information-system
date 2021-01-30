package com.pharmacy.cpis.controller;

import com.pharmacy.cpis.dto.JwtAuthenticationRequest;
import com.pharmacy.cpis.dto.UserTokenState;
import com.pharmacy.cpis.model.UserAcc;
import com.pharmacy.cpis.security.TokenUtils;
import com.pharmacy.cpis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

// Controller in charge of user authentication
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;


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
        UserAcc user = (UserAcc) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Return the token in response to successful authentication
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }
}
