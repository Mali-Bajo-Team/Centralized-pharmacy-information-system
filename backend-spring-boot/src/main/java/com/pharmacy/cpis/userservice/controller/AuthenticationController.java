package com.pharmacy.cpis.userservice.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.userservice.dto.JwtAuthenticationRequest;
import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.dto.UserTokenState;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.SystemAdministrator;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.IPatientRegistrationService;
import com.pharmacy.cpis.userservice.service.ISupplierService;
import com.pharmacy.cpis.userservice.service.ISystemAdministratorService;
import com.pharmacy.cpis.userservice.service.IUserService;
import com.pharmacy.cpis.util.exceptions.PSConflictException;

// Controller in charge of user authentication
@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

	@Autowired
	private Environment env;

	@Autowired
	private IUserService userService;

	@Autowired
	private IPatientRegistrationService patientRegistrationService;
	
    @Autowired
    private IConsultantService consultantService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private ISystemAdministratorService systemAdministratorService;

	// The first endpoint that affects the user when logging in.
	// Then he only knows his username and password and forwards it to the backend.
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody @Valid JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

		UserTokenState token = userService.logIn(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		return ResponseEntity.ok(token);
	}

	// Endpoint to register a new user
	@PostMapping("/signup")
	public ResponseEntity<UserAccount> addUser(@RequestBody @Valid UserRegisterDTO userRequest) {
		UserAccount addedAccount = patientRegistrationService.registerPatient(userRequest);
		return new ResponseEntity<>(addedAccount, HttpStatus.CREATED);
	}

	// GET is because of easy click-activate method
	@GetMapping("activate/{id}")
	public void activateAccount(@PathVariable Long id, HttpServletResponse httpServletResponse) {
		userService.activatePatientAccount(id);
		redirectToLoginPage(httpServletResponse);
	}

	private void redirectToLoginPage(HttpServletResponse httpServletResponse) {
		String projectUrl = env.getProperty("APP_FRONT") + "/";
		httpServletResponse.setHeader("Location", projectUrl);
		httpServletResponse.setStatus(302);
	}

    @PostMapping(value = "/signup/dermatologist", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Consultant> addDermatologist(@RequestBody UserRegisterDTO dermatologist) {
        if (consultantService.existsByEmail(dermatologist.getEmail()))
            throw new PSConflictException("The email is already taken.");

        Consultant addedConsultant = consultantService.registerConsultant(dermatologist, ConsultantType.DERMATOLOGIST);
        return new ResponseEntity<>(addedConsultant, HttpStatus.CREATED);
    }

    @PostMapping(value = "/signup/supplier", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Supplier> addSupplier(@RequestBody UserRegisterDTO supplier) {
        if (supplierService.existsByEmail(supplier.getEmail()))
            throw new PSConflictException("The email is already taken.");

        Supplier addedSupplier = supplierService.registerSupplier(supplier);
        return new ResponseEntity<>(addedSupplier, HttpStatus.CREATED);
    }

    @PostMapping(value = "/signup/admin", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemAdministrator> addSystemAdministrator(@RequestBody UserRegisterDTO systemAdministrator) {
        if (systemAdministratorService.existsByEmail(systemAdministrator.getEmail()))
            throw new PSConflictException("The email is already taken.");

        SystemAdministrator addedSystemAdministrator = systemAdministratorService.registerSystemAdministrator(systemAdministrator);
        return new ResponseEntity<>(addedSystemAdministrator, HttpStatus.CREATED);
    }

}
