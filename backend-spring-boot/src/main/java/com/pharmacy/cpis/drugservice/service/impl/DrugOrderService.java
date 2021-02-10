package com.pharmacy.cpis.drugservice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.drugservice.dto.AddDrugOrderDTO;
import com.pharmacy.cpis.drugservice.dto.AddOrderedDrugDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrderStatus;
import com.pharmacy.cpis.drugservice.model.drugprocurement.OrderedDrug;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.drugservice.repository.IAvailableDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugOrderRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IOrderedDrugRepository;
import com.pharmacy.cpis.drugservice.service.IDrugOrderService;
import com.pharmacy.cpis.userservice.model.users.PharmacyAdministrator;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IPharmacyAdministratorRepository;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class DrugOrderService implements IDrugOrderService {

	@Autowired
	private IDrugOrderRepository drugOrderRepository;

	@Autowired
	private IPharmacyAdministratorRepository pharmacyAdminRepository;

	@Autowired
	private IDrugRepository drugRepository;

	@Autowired
	private IOrderedDrugRepository orderedDrugRepository;

	@Autowired
	private IAvailableDrugRepository availableDrugRepository;

	@Override
	public List<DrugOrder> findAll() {
		return drugOrderRepository.findAll();
	}

	@Override
	public List<DrugOrder> findByPharmacyId(Long pharmacyId) {
		return drugOrderRepository.findAllByPharmacyId(pharmacyId);
	}

	@Override
	public DrugOrder findById(Long id) {
		DrugOrder order = drugOrderRepository.findById(id).orElse(null);

		if (order == null)
			throw new PSNotFoundException("Drug order with the requested id does not exist.");

		return order;
	}

	@Override
	public DrugOrder add(UserAccount creator, AddDrugOrderDTO drugOrder) {
		PharmacyAdministrator admin = pharmacyAdminRepository.findByAccount(creator).orElse(null);

		if (admin == null)
			throw new PSForbiddenException("No pharmacy administrator associated with this account.");

		DrugOrder newDrugOrder = new DrugOrder();
		newDrugOrder.setDeadline(drugOrder.getDeadline());
		newDrugOrder.setStatus(DrugOrderStatus.WAITING_FOR_OFFERS);
		newDrugOrder.setTimestamp(new Date());
		newDrugOrder.setAdministrator(admin);
		newDrugOrder.setPharmacy(admin.getPharmacy());

		DrugOrder saved = drugOrderRepository.save(newDrugOrder);

		for (AddOrderedDrugDTO orderedDrugDTO : drugOrder.getOrderedDrugs()) {
			addDrugToOder(saved, orderedDrugDTO);
		}

		return saved;
	}

	@Override
	public void delete(UserAccount user, Long orderId) {
		PharmacyAdministrator admin = pharmacyAdminRepository.findByAccount(user).orElse(null);

		if (admin == null)
			throw new PSForbiddenException("No pharmacy administrator associated with this account.");

		DrugOrder order = findById(orderId);

		if (!order.getAdministrator().equals(admin))
			throw new PSForbiddenException("Drug orders can only be deleted by their creator.");

		if (!order.getStatus().equals(DrugOrderStatus.WAITING_FOR_OFFERS))
			throw new PSConflictException("Drug order cannot be deleted because it already has offers.");

		drugOrderRepository.delete(order);
	}

	public void update(UserAccount user, Long orderId, AddDrugOrderDTO orderUpdate) {
		PharmacyAdministrator admin = pharmacyAdminRepository.findByAccount(user).orElse(null);

		if (admin == null)
			throw new PSForbiddenException("No pharmacy administrator associated with this account.");

		DrugOrder order = findById(orderId);

		if (!order.getAdministrator().equals(admin))
			throw new PSForbiddenException("Drug orders can only be updated by their creator.");

		if (!order.getStatus().equals(DrugOrderStatus.WAITING_FOR_OFFERS))
			throw new PSConflictException("Drug order cannot be updated because it already has offers.");
		
		order.setDeadline(orderUpdate.getDeadline());
		order = drugOrderRepository.save(order);

		for (OrderedDrug orderedDrug : order.getOrderedDrugs()) {
			orderedDrugRepository.delete(orderedDrug);
		}

		for (AddOrderedDrugDTO orderedDrugDTO : orderUpdate.getOrderedDrugs()) {
			addDrugToOder(order, orderedDrugDTO);
		}
	}

	private void addDrugToOder(DrugOrder order, AddOrderedDrugDTO orderedDrugDTO) {
		Drug drug = drugRepository.findById(orderedDrugDTO.getCode()).orElse(null);

		if (drug == null)
			throw new PSBadRequestException("Drug with code " + orderedDrugDTO.getCode() + " does not exist.");

		OrderedDrug orderedDrug = new OrderedDrug();
		orderedDrug.setOrder(order);
		orderedDrug.setAmount(orderedDrugDTO.getAmount());
		orderedDrug.setDrug(drug);

		AvailableDrug availableDrug = availableDrugRepository
				.findByPharmacyIdAndDrugCode(order.getPharmacy().getId(), drug.getCode()).orElse(null);

		if (availableDrug == null) {
			availableDrug = new AvailableDrug();
			availableDrug.setDrug(drug);
			availableDrug.setDefaultPrice(orderedDrugDTO.getDefaultPrice());
			availableDrug.setPharmacy(order.getPharmacy());
			availableDrug.setAvailableAmount(0);

			availableDrugRepository.save(availableDrug);
		}

		orderedDrugRepository.save(orderedDrug);
	}
}
