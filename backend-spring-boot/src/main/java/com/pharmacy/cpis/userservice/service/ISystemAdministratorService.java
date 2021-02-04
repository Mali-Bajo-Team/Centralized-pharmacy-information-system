package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.SystemAdministrator;

public interface ISystemAdministratorService {
    SystemAdministrator registerSystemAdministrator(UserRegisterDTO systemAdministrator);

    Boolean existsByEmail(String email);
}
