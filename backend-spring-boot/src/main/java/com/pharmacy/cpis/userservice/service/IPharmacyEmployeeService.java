package com.pharmacy.cpis.userservice.service;

import java.util.Collection;

import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.userservice.dto.EmployDermatologistDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;

public interface IPharmacyEmployeeService {

	void fireConsultant(Long pharmacyId, Long consultantId);

	Collection<Consultant> getDermatologistsWhoDontWorkInPharmacy(Long pharmacyId);

	WorkingTimes employDermatologist(Long pharmacyId, EmployDermatologistDTO details);

}
