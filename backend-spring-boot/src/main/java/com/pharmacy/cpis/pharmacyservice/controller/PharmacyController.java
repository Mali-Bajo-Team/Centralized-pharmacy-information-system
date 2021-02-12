package com.pharmacy.cpis.pharmacyservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.drugservice.dto.DrugCodeAndAmountDTO;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.pharmacyservice.dto.PharmaciesForDermatologistDTO;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDTO;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDetailsDTO;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyRegisterDTO;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyTotalPriceDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IUserService;

@RestController
@RequestMapping(value = "api/pharmacies")
public class PharmacyController {

	@Autowired
	private IPharmacyService pharmacyService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IWorkingTimesService workingTimesService;

	@Autowired
	private IAvailableDrugService availableDrugService;

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<PharmacyDetailsDTO> getPharmacyById(@PathVariable(required = true) Long id,
			Authentication authentication) {
		Pharmacy pharmacy = pharmacyService.getById(id);
		return ResponseEntity.ok(new PharmacyDetailsDTO(pharmacy, authentication.getName()));
	}

	@PostMapping("/{id}/subscribe")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Void> subscribeOnPharmacyPromotions(@PathVariable(required = true) Long id,
			@RequestBody PatientEmailDTO patientEmailDTO, Authentication authentication) {
		pharmacyService.subscribePatientOnPharmacyPromotions(authentication.getName(), id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/{id}/unsubscribe")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Void> unsubscribeOnPharmacyPromotions(@PathVariable(required = true) Long id,
			@RequestBody PatientEmailDTO patientEmailDTO, Authentication authentication) {
		pharmacyService.unsubscribePatientOnPharmacyPromotions(authentication.getName(), id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
		List<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for (Pharmacy pharmacy : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(pharmacy));
		}
		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/register", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Pharmacy> addPharmacy(@RequestBody PharmacyRegisterDTO pharmacyRegisterDTO) {
		Pharmacy addedPharmacy = pharmacyService.registerPharmacy(pharmacyRegisterDTO);
		return new ResponseEntity<>(addedPharmacy, HttpStatus.CREATED);
	}

	@PostMapping(value = "/allfordermatologist", consumes = "application/json")
	@PreAuthorize("hasRole('DERMATOLOGIST')")
	public ResponseEntity<List<PharmacyDTO>> getAllPharmaciesWhereWorkingDermatolgoist(
			@RequestBody PharmaciesForDermatologistDTO pharmaciesForDermatologistDTO) {
		UserAccount userAccount = userService.findByEmail(pharmaciesForDermatologistDTO.getDermatologistEmail());
		List<Pharmacy> pharmacies = workingTimesService.dermatologistWorkingPharmacies(userAccount.getId());
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for (Pharmacy pharmacy : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(pharmacy));
		}
		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/requireddrugs")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<PharmacyTotalPriceDTO>> findPharmaciesWithRequiredDrugsAmount(
			@RequestBody List<DrugCodeAndAmountDTO> drugCodeAndAmountDTOS) {
		List<PharmacyTotalPriceDTO> pharmacyTotalPriceDTOS = availableDrugService
				.findPharmaciesWithRequiredDrugsAmount(drugCodeAndAmountDTOS);
		return new ResponseEntity<>(pharmacyTotalPriceDTOS, HttpStatus.OK);
	}
}
