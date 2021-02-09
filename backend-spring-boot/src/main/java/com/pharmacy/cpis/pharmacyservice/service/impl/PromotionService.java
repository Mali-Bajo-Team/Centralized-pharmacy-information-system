package com.pharmacy.cpis.pharmacyservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.pharmacyservice.dto.PromotionDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.model.promotions.Promotion;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.pharmacyservice.repository.IPromotionRepository;
import com.pharmacy.cpis.pharmacyservice.service.IPromotionService;
import com.pharmacy.cpis.util.DateRange;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class PromotionService implements IPromotionService {

	@Autowired
	private IPromotionRepository promotionRepository;

	@Autowired
	private IPharmacyRepository pharmacyRepository;

	@Override
	public Iterable<Promotion> getByPharmacy(Long pharmacyId) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);
		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		return promotionRepository.findByPharmacy(pharmacy);
	}

	@Override
	public Promotion add(Long pharmacyId, PromotionDTO promotion) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);
		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		if (promotion.getValidityStart().compareTo(promotion.getValidityEnd()) > 0)
			throw new PSBadRequestException("The validity period start must be before the end.");

		Promotion newPromotion = new Promotion();
		newPromotion.setTitle(promotion.getTitle());
		newPromotion.setContent(promotion.getContent());
		newPromotion.setPharmacy(pharmacy);
		newPromotion.setValidityPeriod(new DateRange(promotion.getValidityStart(), promotion.getValidityEnd()));

		return promotionRepository.save(newPromotion);
	}
}
