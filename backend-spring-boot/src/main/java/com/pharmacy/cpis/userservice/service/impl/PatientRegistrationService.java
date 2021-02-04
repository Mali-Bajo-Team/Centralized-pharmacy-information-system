package com.pharmacy.cpis.userservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.userservice.dto.UserActivationDTO;
import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.EmailService;
import com.pharmacy.cpis.userservice.service.IAuthorityService;
import com.pharmacy.cpis.userservice.service.IPatientRegistrationService;
import com.pharmacy.cpis.util.exceptions.PSAlreadyExistsException;

@Service
public class PatientRegistrationService implements IPatientRegistrationService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IPatientRepository patientRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IAuthorityService authService;

	@Override
	public UserAccount registerPatient(UserRegisterDTO user) {
		if (userRepository.existsByEmail(user.getEmail()))
			throw new PSAlreadyExistsException("The email is already taken.");
		Patient addedPatient = addNewPatient(user);
		UserAccount addedAccount = addNewPatientAccount(user, addedPatient);
		sendActivationEmail(addedAccount);
		return addedAccount;
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
		} catch (Exception e) {
			System.out.println("Error during sending email: " + e.getMessage());
		}
	}


    private UserAccount addNewPatientAccount(UserRegisterDTO userRequest, Patient addedPatient) {
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setEmail(userRequest.getEmail());
        newUserAccount.setPassword( passwordEncoder.encode(userRequest.getPassword()));
        newUserAccount.setActive(false);
        List<Authority> auth = authService.findByName("ROLE_PATIENT");
        newUserAccount.setAuthorities(auth);
        newUserAccount.setPerson(addedPatient);
		UserAccount addedAccount = userRepository.save(newUserAccount);
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
		Patient addedPatient = patientRepository.save(newPatient);
		return addedPatient;
	}
}
