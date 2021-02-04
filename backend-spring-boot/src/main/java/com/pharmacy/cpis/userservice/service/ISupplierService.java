package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Supplier;

public interface ISupplierService {

    Supplier registerSupplier(UserRegisterDTO supplier);

    Boolean existsByEmail(String email);
}
