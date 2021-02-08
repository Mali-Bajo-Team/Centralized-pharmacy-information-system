package com.pharmacy.cpis.drugservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.drugservice.dto.SupplierOfferDTO;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrderStatus;
import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.drugservice.model.drugprocurement.OfferStatus;
import com.pharmacy.cpis.drugservice.model.drugprocurement.OrderedDrug;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.drugservice.repository.IAvailableDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugOrderRepository;
import com.pharmacy.cpis.drugservice.repository.IOfferRepository;
import com.pharmacy.cpis.drugservice.service.IOfferService;
import com.pharmacy.cpis.userservice.model.users.PharmacyAdministrator;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IPharmacyAdministratorRepository;
import com.pharmacy.cpis.userservice.service.EmailService;
import com.pharmacy.cpis.userservice.service.ISupplierService;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class OfferService implements IOfferService {

	@Autowired
	private IOfferRepository offerRepository;

	@Autowired
	private IDrugOrderRepository drugOrderRepository;

	@Autowired
	private ISupplierService supplierService;

	@Autowired
	private IPharmacyAdministratorRepository pharmacyAdminRepository;

	@Autowired
	private IAvailableDrugRepository availableDrugRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public List<Offer> findOffersBySupplier(Supplier supplier) {
		return offerRepository.findOffersBySupplier(supplier);
	}

	@Override
	public Offer updateOffer(SupplierOfferDTO offerDTO) {
		Offer offer = offerRepository.findById(offerDTO.getId()).orElse(null);
		if (offer == null)
			throw new PSNotFoundException("Not found that offer");
		offer.setPrice(offerDTO.getPrice());
		offer.setShipmentDate(offerDTO.getShipmentDate());
		return offerRepository.save(offer);
	}

	@Override
	public Offer saveOffer(SupplierOfferDTO offerDTO) {
		Offer offer = new Offer();

		DrugOrder order = drugOrderRepository.findById(offerDTO.getOrder().getId()).orElse(null);

		offer.setShipmentDate(offerDTO.getShipmentDate());
		offer.setPrice(offerDTO.getPrice());
		offer.setStatus(OfferStatus.PENDING);
		if (supplierService.getLoggedSupplier() == null)
			throw new PSBadRequestException("There is no logged supplier");
		offer.setSupplier(supplierService.getLoggedSupplier());
		if (order == null)
			throw new PSBadRequestException("There is no drg order with that id");
		offer.setOrder(drugOrderRepository.findById(offerDTO.getOrder().getId()).orElse(null));

		order.setStatus(DrugOrderStatus.WAITING_FOR_SELECTION);

		drugOrderRepository.save(order);
		return offerRepository.save(offer);
	}

	public void accept(UserAccount user, Long offerId) {
		PharmacyAdministrator admin = pharmacyAdminRepository.findByAccount(user).orElse(null);
		if (admin == null)
			throw new PSForbiddenException("No pharmacy administrator associated with this account.");

		Offer offerToAccept = offerRepository.findById(offerId).orElse(null);
		if (offerToAccept == null)
			throw new PSNotFoundException("The requested offer does not exist.");

		DrugOrder order = offerToAccept.getOrder();

		if (!order.getAdministrator().equals(admin))
			throw new PSForbiddenException("Offers can only be accepted by the order's creator.");

		if (order.getStatus().equals(DrugOrderStatus.FINISHED))
			throw new PSConflictException("The requested drug order is already finished.");

		order.setStatus(DrugOrderStatus.FINISHED);
		drugOrderRepository.save(order);
		updateAmounts(order);
		updateOffers(order, offerToAccept);
	}

	private void updateAmounts(DrugOrder order) {
		for (OrderedDrug drug : order.getOrderedDrugs()) {
			AvailableDrug availableDrug = availableDrugRepository
					.findByPharmacyIdAndDrugCode(order.getPharmacy().getId(), drug.getDrug().getCode()).orElse(null);

			if (availableDrug == null)
				throw new PSConflictException(
						"Drug with code " + drug.getDrug().getCode() + " is not available in the pharmacy.");

			availableDrug.setAvailableAmount(availableDrug.getAvailableAmount() + drug.getAmount());
			availableDrugRepository.save(availableDrug);
		}
	}

	private void updateOffers(DrugOrder order, Offer offerToAccept) {
		for (Offer offer : order.getOffers()) {
			if (offer.equals(offerToAccept))
				offer.setStatus(OfferStatus.ACCEPTED);
			else
				offer.setStatus(OfferStatus.REJECTED);

			offerRepository.save(offer);
			try {
				emailService.sendOfferUpdateEmailAsync(offer.getSupplier().getAccount().getEmail(), offer);
			} catch (Exception e) {
				System.out.println("Email sending failed!");
			}
		}
	}
}
