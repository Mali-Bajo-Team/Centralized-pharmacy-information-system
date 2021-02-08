package com.pharmacy.cpis.scheduleservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.scheduleservice.dto.VacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.service.IVacationRequestService;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;

@RestController
@RequestMapping(value = "api/vacationrequst")
public class VacationRequestController {
	@Autowired
	private IVacationRequestService vacationRequestService;

	@PostMapping("/createrequest")
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<VacationRequestDTO> getAllConsultationsForConsultant(
			@RequestBody VacationRequestDTO vacationRequestDTO) {

		VacationRequestDTO vacationRequest = vacationRequestService.createVacationRequest(vacationRequestDTO);

		return new ResponseEntity<>(vacationRequest, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<String> getVacationRequests(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		return ResponseEntity.ok(user.getRole());
	}

}
