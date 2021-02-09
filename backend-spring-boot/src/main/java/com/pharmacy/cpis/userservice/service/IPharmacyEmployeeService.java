package com.pharmacy.cpis.userservice.service;

import java.util.Collection;

import com.pharmacy.cpis.userservice.model.users.Consultant;

public interface IPharmacyEmployeeService {

	void fireConsultant(Long pharmacyId, Long consultantId);

	Collection<Consultant> getDermatologistsWhoDontWorkInPharmacy(Long pharmacyId);
	
}
