package com.pharmacy.cpis.drugservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.drugservice.dto.AddAvailableDrugDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.repository.IAvailableDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IReservationRepository;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class AvailableDrugService implements IAvailableDrugService {

	@Autowired
	private IAvailableDrugRepository availableDrugRepository;

	@Autowired
	private IPharmacyRepository pharmacyRepository;

	@Autowired
	private IDrugRepository drugRepository;

	@Autowired
	private IReservationRepository reservationRepository;

	public Iterable<AvailableDrug> getByPharmacy(Long pharmacyId) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);

		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		return pharmacy.getAvailableDrugs();
	}

	public AvailableDrug addToPharmacy(Long pharmacyId, AddAvailableDrugDTO drugInfo) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);
		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		Drug drug = drugRepository.findById(drugInfo.getCode()).orElse(null);
		if (drug == null)
			throw new PSNotFoundException("The requested drug does not exist.");

		for (AvailableDrug availableDrug : pharmacy.getAvailableDrugs()) {
			if (availableDrug.getDrug().getCode().equals(drug.getCode()))
				throw new PSConflictException(
						"The requested drug is already available in the requested pharmacy.");
		}

		AvailableDrug availableDrug = new AvailableDrug();
		availableDrug.setPharmacy(pharmacy);
		availableDrug.setDrug(drug);
		availableDrug.setDefaultPrice(drugInfo.getDefaultPrice());
		availableDrug.setAvailableAmount(0);

		return availableDrugRepository.save(availableDrug);
	}

	public void deleteFromPharmacy(Long pharmacyId, String drugCode) {
		AvailableDrug availableDrug = availableDrugRepository.findByPharmacyIdAndDrugCode(pharmacyId, drugCode)
				.orElse(null);

		if (availableDrug == null)
			throw new PSConflictException("The requested drug is not available in the requested pharmacy.");

		for (Reservation reservation : reservationRepository.findAllByPharmacyIdAndDrugCode(pharmacyId, drugCode)) {
			if (!reservation.getIsPickedUp())
				throw new PSConflictException(
						"The requestes drug cannot be deleted because there are unfinished reservations.");
		}

		availableDrugRepository.delete(availableDrug);
	}
	
	public AvailableDrug getByPharmacyAndDrug(Long pharmacyId, String drugCode) {
		AvailableDrug availableDrug = availableDrugRepository.findByPharmacyIdAndDrugCode(pharmacyId, drugCode)
				.orElse(null);

		if (availableDrug == null)
			throw new PSConflictException("The requested drug is not available in the requested pharmacy.");
		
		return availableDrug;
	}

}
