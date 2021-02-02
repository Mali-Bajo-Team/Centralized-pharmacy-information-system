package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

public interface IPatientRegistrationService {

    UserAccount registerPatient(UserRegisterDTO user);

    Boolean existsByEmail(String email);
}
