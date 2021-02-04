package com.pharmacy.cpis.userservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.userservice.dto.PasswordChangeDTO;
import com.pharmacy.cpis.userservice.dto.PersonalInformationDTO;
import com.pharmacy.cpis.userservice.model.users.Person;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IPersonService;
import com.pharmacy.cpis.userservice.service.IUserService;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;

@RestController
@RequestMapping(value = "api/profile")
public class ProfileController {

	@Autowired
	private IPersonService personService;
	@Autowired
	private IUserService userAccountService;

	@GetMapping
	@EmployeeAccountActive
	public ResponseEntity<PersonalInformationDTO> getProfile() {
		UserAccount account = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person person = personService.getByUserAccount(account);

		return ResponseEntity.ok(new PersonalInformationDTO(person));
	}

	@PutMapping(consumes = "application/json")
	@EmployeeAccountActive
	public ResponseEntity<PersonalInformationDTO> updateProfile(
			@RequestBody @Valid PersonalInformationDTO personalInfo) {
		UserAccount account = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person person = personService.update(account, personalInfo);

		return ResponseEntity.ok(new PersonalInformationDTO(person));
	}
	
	@PutMapping(value = "/password", consumes = "application/json")
	public ResponseEntity<Void> changePassword(@RequestBody @Valid PasswordChangeDTO password) {
		userAccountService.changePassword(password.getOldPassword(), password.getNewPassword());

		return ResponseEntity.noContent().build();
	}

}
