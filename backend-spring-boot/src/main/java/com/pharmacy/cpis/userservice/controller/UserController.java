package com.pharmacy.cpis.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.userservice.dto.PasswordChangeDTO;
import com.pharmacy.cpis.userservice.dto.UserAccDTO;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IUserService;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

	@Autowired
	private IUserService userAccountService;

	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<List<UserAccDTO>> getAllUsers() {

		List<UserAccount> usersACC = userAccountService.findAll();

		// convert users to DTOs
		List<UserAccDTO> usersAccDTO = new ArrayList<>();
		for (UserAccount u : usersACC) {
			usersAccDTO.add(new UserAccDTO(u));
		}

		return new ResponseEntity<>(usersAccDTO, HttpStatus.OK);
	}

	@CrossOrigin
	@PutMapping(value = "/password", consumes = "application/json")
	public ResponseEntity<Void> changePassword(@RequestBody @Valid PasswordChangeDTO password) {
		userAccountService.changePassword(password.getOldPassword(), password.getNewPassword());

		return ResponseEntity.noContent().build();
	}
}
