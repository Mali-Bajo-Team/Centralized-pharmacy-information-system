package com.pharmacy.cpis.pharmacyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDetailsDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;

@RestController
@RequestMapping(value = "api/pharmacy")
@PreAuthorize("hasRole('PHARMACY_ADMIN')")
public class PharmacyProfileController {

	@Autowired
	private IPharmacyService pharmacyService;

	@GetMapping
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<PharmacyDetailsDTO> getPharmacy() {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Pharmacy pharmacy = pharmacyService.getById(user.getPharmacyId());

		return ResponseEntity.ok(new PharmacyDetailsDTO(pharmacy));
	}

}
