package com.pharmacy.cpis.drugservice.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.drugservice.dto.AddAvailableDrugDTO;
import com.pharmacy.cpis.drugservice.dto.AddDrugPriceDTO;
import com.pharmacy.cpis.drugservice.dto.AvailableDrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugPriceDTO;
import com.pharmacy.cpis.drugservice.dto.DrugPriceSearchDTO;
import com.pharmacy.cpis.drugservice.dto.DrugSearchDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;

@RestController
@RequestMapping("api/pharmacy/drugs")
public class DrugInPharmacyController {

	@Autowired
	private IAvailableDrugService availableDrugService;

	@GetMapping
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Iterable<AvailableDrugDTO>> getDrugs() {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Collection<AvailableDrugDTO> availableDrugs = CollectionUtil
				.map(availableDrugService.getByPharmacy(user.getPharmacyId()), drug -> new AvailableDrugDTO(drug));

		return ResponseEntity.ok(availableDrugs);
	}

	@PostMapping(value = "/search", consumes = "application/json")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Iterable<AvailableDrugDTO>> searchDrugs(@RequestBody @Valid DrugSearchDTO searchDTO) {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Collection<AvailableDrugDTO> availableDrugs = CollectionUtil.map(
				availableDrugService.searchByPharmacy(user.getPharmacyId(), searchDTO),
				drug -> new AvailableDrugDTO(drug));

		return ResponseEntity.ok(availableDrugs);
	}

	@PostMapping(value = "", consumes = "application/json")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<AvailableDrugDTO> addDrug(@RequestBody @Valid AddAvailableDrugDTO drugInfo) {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		AvailableDrug added = availableDrugService.addToPharmacy(user.getPharmacyId(), drugInfo);

		return new ResponseEntity<AvailableDrugDTO>(new AvailableDrugDTO(added), HttpStatus.CREATED);
	}

	@GetMapping("/{code}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<AvailableDrugDTO> getDrug(@PathVariable(required = true) String code) {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		AvailableDrug drug = availableDrugService.getByPharmacyAndDrug(user.getPharmacyId(), code);

		return ResponseEntity.ok(new AvailableDrugDTO(drug));
	}

	@DeleteMapping("/{code}")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Void> deleteDrug(@PathVariable(required = true) String code) {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		availableDrugService.deleteFromPharmacy(user.getPharmacyId(), code);

		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/{code}/price/search", consumes = "application/json")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Collection<DrugPriceDTO>> getDrugPrice(@PathVariable(required = true) String code,
			@RequestBody @Valid DrugPriceSearchDTO dates) {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Collection<DrugPriceDTO> prices = availableDrugService.getPrice(user.getPharmacyId(), code, dates.getStart(),
				dates.getEnd());

		return ResponseEntity.ok(prices);
	}

	@PostMapping(value = "/{code}/price", consumes = "application/json")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Void> addDrugPrice(@PathVariable(required = true) String code,
			@RequestBody @Valid AddDrugPriceDTO priceInfo) {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		availableDrugService.addPrice(user.getPharmacyId(), code, priceInfo);

		return ResponseEntity.noContent().build();
	}

}
