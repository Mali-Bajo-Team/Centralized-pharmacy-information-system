package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.ExaminitedPatientDTO;
import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;

import java.util.Set;

public interface IConsultantService {

    Consultant registerDermatologist(UserRegisterDTO dermatologist);

    Boolean existsByEmail(String email);

    Set<ExaminitedPatientDTO> getExaminitedPatients(Long consultantID);
}
