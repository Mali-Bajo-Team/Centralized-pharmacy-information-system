package com.pharmacy.cpis.pharmacyservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.pharmacyservice.dto.PromotionDTO;
import com.pharmacy.cpis.pharmacyservice.model.promotions.Promotion;
import com.pharmacy.cpis.pharmacyservice.service.IPromotionService;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.EmailService;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;

@RestController
@RequestMapping(value = "api/promotions")
public class PromotionController {

	@Autowired
	private IPromotionService promotionService;

	@Autowired
	private EmailService emailService;

	@GetMapping
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Iterable<PromotionDTO>> getPromotions(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Iterable<Promotion> promotions = promotionService.getByPharmacy(user.getPharmacyId());
		List<PromotionDTO> promotionDTOs = new ArrayList<>();

		for (Promotion promotion : promotions) {
			promotionDTOs.add(new PromotionDTO(promotion));
		}

		return ResponseEntity.ok(promotionDTOs);
	}

	@PostMapping
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<PromotionDTO> addPromotion(@RequestBody @Valid PromotionDTO promotion,
			Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Promotion newPromotion = promotionService.add(user.getPharmacyId(), promotion);

		for (Patient subscriber : newPromotion.getPharmacy().getSubscribers()) {
			try {
				emailService.sendPromotionEmailAsync(subscriber.getAccount().getEmail(), newPromotion);
			} catch (Exception e) {
				System.out.println("Email sending failed.");
			}
		}

		return ResponseEntity.ok(new PromotionDTO(newPromotion));
	}

}
