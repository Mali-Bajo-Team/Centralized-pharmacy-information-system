package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.dto.AddDrugOrderDTO;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

import java.util.List;

public interface IDrugOrderService {

	List<DrugOrder> findAll();

	List<DrugOrder> findByPharmacyId(Long pharmacyId);

	DrugOrder add(UserAccount creator, AddDrugOrderDTO drugOrder);

}
