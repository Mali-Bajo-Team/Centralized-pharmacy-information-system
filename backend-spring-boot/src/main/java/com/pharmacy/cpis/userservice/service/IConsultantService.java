package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;

public interface IConsultantService {

    Consultant registerDermatologist(UserRegisterDTO dermatologist);

    Boolean existsByEmail(String email);
}
