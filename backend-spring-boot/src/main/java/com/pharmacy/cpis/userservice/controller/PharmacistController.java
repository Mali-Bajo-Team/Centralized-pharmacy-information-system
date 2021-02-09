package com.pharmacy.cpis.userservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.userservice.dto.ConsultantWithPharmaciesDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.IPharmacyEmployeeService;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;

@RestController
@RequestMapping(value = "api/pharmacists")
public class PharmacistController {

	@Autowired
	private IConsultantService consultantService;

	@Autowired
	private IPharmacyEmployeeService pharmacyEmployeeService;

	@GetMapping
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'PATIENT')")
	@EmployeeAccountActive
	public ResponseEntity<Collection<ConsultantWithPharmaciesDTO>> getAll(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		Collection<Consultant> consultants = null;

		if (user.getRole().equals("PATIENT")) {
			consultants = consultantService.getByType(ConsultantType.PHARMACIST);
		} else {
			if (user.getPharmacyId() == null)
				throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

			consultants = consultantService.getByTypeAndPharmacy(ConsultantType.PHARMACIST, user.getPharmacyId());
		}

		Collection<ConsultantWithPharmaciesDTO> mapped = CollectionUtil.map(consultants,
				consultant -> new ConsultantWithPharmaciesDTO(consultant));

		return ResponseEntity.ok(mapped);
	}

	@GetMapping("/pharmacy/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Collection<ConsultantWithPharmaciesDTO>> getByPharmacy(
			@PathVariable(required = true) Long id) {
		Collection<Consultant> consultants = consultantService.getByTypeAndPharmacy(ConsultantType.PHARMACIST, id);

		Collection<ConsultantWithPharmaciesDTO> mapped = CollectionUtil.map(consultants,
				consultant -> new ConsultantWithPharmaciesDTO(consultant));

		return ResponseEntity.ok(mapped);
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
