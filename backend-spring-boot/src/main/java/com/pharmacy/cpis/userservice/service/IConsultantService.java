package com.pharmacy.cpis.userservice.service;

import java.util.Collection;
import java.util.Set;

import com.pharmacy.cpis.userservice.dto.ExaminitedPatientDTO;
import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;

public interface IConsultantService {

	Consultant registerDermatologist(UserRegisterDTO dermatologist);

	Boolean existsByEmail(String email);

	Set<ExaminitedPatientDTO> getExaminitedPatients(Long consultantID);

	Consultant findByEmail(String email);

	Collection<Consultant> getByType(ConsultantType type);
	
	Collection<Consultant> getByTypeAndPharmacy(ConsultantType type, Long pharmacyId);

}
