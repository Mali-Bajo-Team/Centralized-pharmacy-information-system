package com.pharmacy.cpis.pharmacyservice.service;

import com.pharmacy.cpis.pharmacyservice.dto.PromotionDTO;
import com.pharmacy.cpis.pharmacyservice.model.promotions.Promotion;

public interface IPromotionService {

	Iterable<Promotion> getByPharmacy(Long pharmacyId);

	Promotion add(Long pharmacyId, PromotionDTO promotion);

}
