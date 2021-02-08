package com.pharmacy.cpis.drugservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.drugservice.dto.AddDrugOrderDTO;
import com.pharmacy.cpis.drugservice.dto.DrugOrderDTO;
import com.pharmacy.cpis.drugservice.dto.SupplierOfferDTO;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.drugservice.service.IDrugOrderService;
import com.pharmacy.cpis.drugservice.service.IOfferService;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.ISupplierService;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;

@RestController
@RequestMapping(value = "api/procurement")
public class ProcurementController {

	@Autowired
	private ISupplierService supplierService;

	@Autowired
	private IOfferService offerService;

	@Autowired
	private IDrugOrderService drugOrderService;

	@GetMapping(value = "/offers")
	public ResponseEntity<List<SupplierOfferDTO>> getSupplierOffers() {
		List<SupplierOfferDTO> supplierOfferDTOS = new ArrayList<>();
		for (Offer offer : offerService.findOffersBySupplier(supplierService.getLoggedSupplier())) {
			supplierOfferDTOS.add(new SupplierOfferDTO(offer));
		}
		return new ResponseEntity<>(supplierOfferDTOS, HttpStatus.OK);
	}

	@PutMapping(value = "/offers")
	public ResponseEntity<SupplierOfferDTO> updateSupplierOffer(@RequestBody SupplierOfferDTO supplierOffer) {
		Offer offer = offerService.updateOffer(supplierOffer);
		return new ResponseEntity<>(new SupplierOfferDTO(offer), HttpStatus.OK);
	}

	@PostMapping(value = "/offers")
	public ResponseEntity<SupplierOfferDTO> createSupplierOffer(@RequestBody SupplierOfferDTO supplierOffer) {
		Offer offer = offerService.saveOffer(supplierOffer);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/orders")
	@PreAuthorize("hasAnyRole('PHARMACY_ADMIN', 'SUPPLIER')")
	@EmployeeAccountActive
	public ResponseEntity<List<DrugOrderDTO>> getAllDrugOrders() {
		List<DrugOrderDTO> drugOrderDTOS = new ArrayList<>();
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.getRole().equals("SUPPLIER")) {
			for (DrugOrder drugOrder : drugOrderService.findAll()) {
				drugOrderDTOS.add(new DrugOrderDTO(drugOrder));
			}
		} else {
			if (user.getPharmacyId() == null)
				throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

			for (DrugOrder drugOrder : drugOrderService.findByPharmacyId(user.getPharmacyId())) {
				drugOrderDTOS.add(new DrugOrderDTO(drugOrder));
			}
		}

		return new ResponseEntity<>(drugOrderDTOS, HttpStatus.OK);
	}

	@PostMapping(value = "/orders", consumes = "application/json")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Void> addDrugOrder(@RequestBody @Valid AddDrugOrderDTO drugOrder) {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		drugOrderService.add(user, drugOrder);

		return ResponseEntity.noContent().build();
	}

}
