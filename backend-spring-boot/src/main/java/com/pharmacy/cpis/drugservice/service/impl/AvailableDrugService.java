package com.pharmacy.cpis.drugservice.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmacy.cpis.drugservice.dto.AddAvailableDrugDTO;
import com.pharmacy.cpis.drugservice.dto.AddDrugPriceDTO;
import com.pharmacy.cpis.drugservice.dto.DrugCodeAndAmountDTO;
import com.pharmacy.cpis.drugservice.dto.DrugPriceDTO;
import com.pharmacy.cpis.drugservice.dto.DrugSearchDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrderStatus;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.drugservice.model.drugsales.Price;
import com.pharmacy.cpis.drugservice.repository.IAvailableDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugOrderRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IPriceRepository;
import com.pharmacy.cpis.drugservice.repository.IReservationRepository;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyTotalPriceDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.service.IPatientService;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;
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

	@Autowired
	private IPriceRepository priceRepository;

	@Autowired
	private IDrugOrderRepository drugOrderRepository;

	@Autowired
	private IPatientService patientService;

	@Override
	public Collection<AvailableDrug> getByPharmacyForPatient(Long pharmacyId, String patientEmail) {
		Patient patient = patientService.findByEmail(patientEmail);
		if (patient == null)
			throw new PSForbiddenException("No account found for this email.");

		Collection<AvailableDrug> drugs = getByPharmacy(pharmacyId);
		drugs = CollectionUtil.findAll(drugs, drug -> drug.getAvailableAmount() > 0);
		drugs = CollectionUtil.findAll(drugs, drug -> !patient.getAllergies().contains(drug.getDrug()));

		return drugs;
	}

	public Collection<AvailableDrug> getByPharmacy(Long pharmacyId) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);

		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		return pharmacy.getAvailableDrugs();
	}

	@Override
	public Collection<AvailableDrug> searchByPharmacy(Long pharmacyId, DrugSearchDTO searchDTO) {
		Collection<AvailableDrug> drugs = getByPharmacy(pharmacyId);
		if (searchDTO.getName() != null)
			drugs = CollectionUtil.findAll(drugs, drug -> drug.getDrug().getName().contains(searchDTO.getName()));
		if (searchDTO.getCode() != null)
			drugs = CollectionUtil.findAll(drugs, drug -> drug.getDrug().getCode().contains(searchDTO.getCode()));
		if (searchDTO.getDrugClassId() != null)
			drugs = CollectionUtil.findAll(drugs,
					drug -> drug.getDrug().getDrugClass().getId().equals(searchDTO.getDrugClassId()));
		if (searchDTO.getDrugFormId() != null)
			drugs = CollectionUtil.findAll(drugs,
					drug -> drug.getDrug().getDrugForm().getId().equals(searchDTO.getDrugFormId()));

		return drugs;
	}

	@Override
	public AvailableDrug addToPharmacy(Long pharmacyId, AddAvailableDrugDTO drugInfo) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);
		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		Drug drug = drugRepository.findById(drugInfo.getCode()).orElse(null);
		if (drug == null)
			throw new PSNotFoundException("The requested drug does not exist.");

		if (CollectionUtil.contains(pharmacy.getAvailableDrugs(),
				available -> available.getDrug().getCode().equals(drug.getCode())))
			throw new PSConflictException("The requested drug is already available in the requested pharmacy.");

		AvailableDrug availableDrug = new AvailableDrug();
		availableDrug.setPharmacy(pharmacy);
		availableDrug.setDrug(drug);
		availableDrug.setDefaultPrice(drugInfo.getDefaultPrice());
		availableDrug.setAvailableAmount(0);

		return availableDrugRepository.save(availableDrug);
	}

	@Override
	public void deleteFromPharmacy(Long pharmacyId, String drugCode) {
		AvailableDrug availableDrug = availableDrugRepository.findByPharmacyIdAndDrugCode(pharmacyId, drugCode)
				.orElse(null);

		if (availableDrug == null)
			throw new PSConflictException("The requested drug is not available in the requested pharmacy.");

		if (hasActiveReservations(pharmacyId, drugCode))
			throw new PSConflictException("The requestes drug cannot be deleted because of unfinished reservations.");

		if (hasActiveOrders(pharmacyId, drugCode))
			throw new PSConflictException("The requestes drug cannot be deleted because of unfinished orders.");

		availableDrugRepository.delete(availableDrug);
	}

	@Override
	public List<PharmacyTotalPriceDTO> findPharmaciesWithRequiredDrugsAmount(
			List<DrugCodeAndAmountDTO> drugCodeAndAmountDTOS) {
		List<PharmacyTotalPriceDTO> pharmaciesWithRequiredDrugs = new ArrayList<>();
		for (Pharmacy pharmacy : pharmacyRepository.findAll()) {
			double totalPharmacyPriceForRequiredDrugsAmount = getTotalPharmacyPriceForRequiredDrugsAmount(pharmacy,
					drugCodeAndAmountDTOS);
			if (totalPharmacyPriceForRequiredDrugsAmount != -1
					&& !isPharmacyAlreadyAdded(pharmaciesWithRequiredDrugs, pharmacy)) {
				pharmaciesWithRequiredDrugs
						.add(new PharmacyTotalPriceDTO(pharmacy, totalPharmacyPriceForRequiredDrugsAmount));
			}

		}
		return pharmaciesWithRequiredDrugs;
	}

	private boolean isPharmacyAlreadyAdded(List<PharmacyTotalPriceDTO> pharmaciesWithRequiredDrugs,
			Pharmacy newPharmacy) {
		for (PharmacyTotalPriceDTO pharmacy : pharmaciesWithRequiredDrugs) {
			if (pharmacy.getPharmacyId().equals(newPharmacy.getId()))
				return true;
		}
		return false;
	}

	private double getTotalPharmacyPriceForRequiredDrugsAmount(Pharmacy pharmacy,
			List<DrugCodeAndAmountDTO> drugCodeAndAmountDTOS) {
		double totalPrice = -1;
		for (DrugCodeAndAmountDTO drugCodeAndAmountDTO : drugCodeAndAmountDTOS) {
			double priceForRequiredDrugAmount = getPharmacyPriceForRequiredDrugAmount(pharmacy, drugCodeAndAmountDTO);
			if (priceForRequiredDrugAmount == -1) {
				return -1;
			} else {
				if (totalPrice == -1)
					totalPrice = 0; // Only for init purpose, to start from zero, not -1
				totalPrice += priceForRequiredDrugAmount;
			}

		}
		return totalPrice;
	}

	private double getPharmacyPriceForRequiredDrugAmount(Pharmacy pharmacy, DrugCodeAndAmountDTO drugCodeAndAmountDTO) {
		for (AvailableDrug availableDrug : pharmacy.getAvailableDrugs()) {
			if (availableDrug.getDrug().getCode().equals(drugCodeAndAmountDTO.getDrugCode())
					&& availableDrug.getAvailableAmount() >= drugCodeAndAmountDTO.getAmount()) {
				return drugCodeAndAmountDTO.getAmount() * availableDrug.getDefaultPrice();
				// TODO: Calculate discount if that is even a required ?
			}
		}
		return -1;
	}

	private boolean hasActiveReservations(Long pharmacyId, String drugCode) {
		return CollectionUtil.contains(reservationRepository.findAllByPharmacyIdAndDrugCode(pharmacyId, drugCode),
				res -> !res.getIsPickedUp());
	}

	private boolean hasActiveOrders(Long pharmacyId, String drugCode) {
		Collection<DrugOrder> orders = drugOrderRepository.findAllByPharmacyId(pharmacyId);
		orders = CollectionUtil.findAll(orders, order -> !order.getStatus().equals(DrugOrderStatus.FINISHED));

		for (DrugOrder order : orders) {
			if (CollectionUtil.contains(order.getOrderedDrugs(), drug -> drug.getDrug().getCode().equals(drugCode)))
				return true;
		}

		return false;
	}

	@Override
	public AvailableDrug getByPharmacyAndDrug(Long pharmacyId, String drugCode) {
		AvailableDrug availableDrug = availableDrugRepository.findByPharmacyIdAndDrugCode(pharmacyId, drugCode)
				.orElse(null);

		if (availableDrug == null)
			throw new PSConflictException("The requested drug is not available in the requested pharmacy.");

		return availableDrug;
	}

	@Override
	public AvailableDrug checkIsAvailableinPharmacy(Long pharmacyId, String drugCode) {
		AvailableDrug availableDrug = availableDrugRepository.findByPharmacyIdAndDrugCode(pharmacyId, drugCode)
				.orElse(null);

		if (availableDrug == null)
			return null;

		return availableDrug;
	}

	@Override
	public Collection<DrugPriceDTO> getPrice(Long pharmacyId, String drugCode, Date start, Date end) {
		AvailableDrug availableDrug = availableDrugRepository.findByPharmacyIdAndDrugCode(pharmacyId, drugCode)
				.orElse(null);

		if (availableDrug == null)
			throw new PSConflictException("The requested drug is not available in the requested pharmacy.");

		Collection<DrugPriceDTO> prices = new ArrayList<>();

		for (Date date : DateConversionsAndComparisons.getDatesBetween(start, end)) {
			prices.add(new DrugPriceDTO(date, availableDrug.findPrice(date).getPrice()));
		}

		return prices;
	}

	@Override
	@Transactional
	public void addPrice(Long pharmacyId, String drugCode, AddDrugPriceDTO priceInfo) {
		AvailableDrug availableDrug = availableDrugRepository.findByPharmacyIdAndDrugCode(pharmacyId, drugCode)
				.orElse(null);

		if (availableDrug == null)
			throw new PSConflictException("The requested drug is not available in the requested pharmacy.");

		for (Date date : DateConversionsAndComparisons.getDatesBetween(priceInfo.getStart(), priceInfo.getEnd())) {
			Price price = availableDrug.findPrice(date);
			price.setPrice(priceInfo.getPrice());
			priceRepository.save(price);
		}
	}

	@Override
	@Transactional
	public AvailableDrug updateAmount(Long pharmacyId, String drugCode, Integer amount) {
		AvailableDrug availableDrug = availableDrugRepository.findByPharmacyIdAndDrugCode(pharmacyId, drugCode)
				.orElse(null);
		if (availableDrug == null)
			throw new PSBadRequestException("There is no available drug.");

		int amountOfDrug = availableDrug.getAvailableAmount() - amount;
		if (amountOfDrug < 0) {
			throw new PSBadRequestException("There is no available drug in requested amount.");
		}
		availableDrug.setAvailableAmount(amountOfDrug);

		return availableDrugRepository.save(availableDrug);
	}

}
