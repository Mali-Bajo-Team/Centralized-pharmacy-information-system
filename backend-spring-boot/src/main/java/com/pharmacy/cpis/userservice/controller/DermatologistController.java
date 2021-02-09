package com.pharmacy.cpis.userservice.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.scheduleservice.dto.WorkingTimesDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import com.pharmacy.cpis.userservice.dto.ConsultantWithBusyTimesDTO;
import com.pharmacy.cpis.userservice.dto.ConsultantWithPharmaciesDTO;
import com.pharmacy.cpis.userservice.dto.EmployDermatologistDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.IPharmacyEmployeeService;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@RestController
@RequestMapping(value = "api/dermatologists")
public class DermatologistController {

	@Autowired
	private IConsultantService consultantService;

	@Autowired
	private IPharmacyEmployeeService pharmacyEmployeeService;

	@Autowired
	private IWorkingTimesService workingTimesService;

	@GetMapping
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'PATIENT')")
	@EmployeeAccountActive
	public ResponseEntity<Collection<ConsultantWithPharmaciesDTO>> getAll(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		Collection<Consultant> consultants = null;

		if (user.getRole().equals("PATIENT")) {
			consultants = consultantService.getByType(ConsultantType.DERMATOLOGIST);
		} else {
			if (user.getPharmacyId() == null)
				throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

			consultants = consultantService.getByTypeAndPharmacy(ConsultantType.DERMATOLOGIST, user.getPharmacyId());
		}

		Collection<ConsultantWithPharmaciesDTO> mapped = CollectionUtil.map(consultants,
				consultant -> new ConsultantWithPharmaciesDTO(consultant));

		return ResponseEntity.ok(mapped);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Void> employ(Authentication authentication,
			@RequestBody @Valid EmployDermatologistDTO details) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		pharmacyEmployeeService.employDermatologist(user.getPharmacyId(), details);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/notemployees")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Collection<ConsultantWithBusyTimesDTO>> getNotEmployees(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Collection<Consultant> consultants = pharmacyEmployeeService
				.getDermatologistsWhoDontWorkInPharmacy(user.getPharmacyId());

		Collection<ConsultantWithBusyTimesDTO> mapped = CollectionUtil.map(consultants,
				consultant -> new ConsultantWithBusyTimesDTO(consultant));

		return ResponseEntity.ok(mapped);
	}

	@GetMapping("/pharmacy/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Collection<ConsultantWithPharmaciesDTO>> getByPharmacy(
			@PathVariable(required = true) Long id) {
		Collection<Consultant> consultants = consultantService.getByTypeAndPharmacy(ConsultantType.DERMATOLOGIST, id);

		Collection<ConsultantWithPharmaciesDTO> mapped = CollectionUtil.map(consultants,
				consultant -> new ConsultantWithPharmaciesDTO(consultant));

		return ResponseEntity.ok(mapped);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<WorkingTimesDTO> getWorkingTimes(@PathVariable(required = true) Long id,
			Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		WorkingTimes workingTimes = workingTimesService.getByConsultantAndPharmacy(user.getPharmacyId(), id);
		if (!workingTimes.getConsultant().getType().equals(ConsultantType.DERMATOLOGIST))
			throw new PSNotFoundException("The requested dermatologist does not exist.");

		return ResponseEntity.ok(new WorkingTimesDTO(workingTimes));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Void> fire(@PathVariable(required = true) Long id, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		pharmacyEmployeeService.fireConsultant(user.getPharmacyId(), id);

		return ResponseEntity.noContent().build();
	}

}
