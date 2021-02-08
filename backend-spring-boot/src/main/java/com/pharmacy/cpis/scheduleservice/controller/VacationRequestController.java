package com.pharmacy.cpis.scheduleservice.controller;

import java.util.Collection;

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

import com.pharmacy.cpis.scheduleservice.dto.AddVacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.dto.VacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.scheduleservice.service.IVacationRequestService;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;

@RestController
@RequestMapping(value = "api/vacationrequst")
public class VacationRequestController {
	@Autowired
	private IVacationRequestService vacationRequestService;

	@PostMapping("/createrequest")
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<AddVacationRequestDTO> getAllConsultationsForConsultant(
			@RequestBody AddVacationRequestDTO vacationRequestDTO) {

		AddVacationRequestDTO vacationRequest = vacationRequestService.createVacationRequest(vacationRequestDTO);

		return new ResponseEntity<>(vacationRequest, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Collection<VacationRequestDTO>> getVacationRequests(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		Collection<VacationRequest> requests = null;

		if (user.getRole().equals("ADMIN")) {
			requests = vacationRequestService.getDermatologistRequests();
		} else {
			requests = vacationRequestService.getPharmacistRequestsByPharmacy(user.getPharmacyId());
		}

		Collection<VacationRequestDTO> mapped = CollectionUtil.map(requests,
				request -> new VacationRequestDTO(request));

		return ResponseEntity.ok(mapped);
	}

}
