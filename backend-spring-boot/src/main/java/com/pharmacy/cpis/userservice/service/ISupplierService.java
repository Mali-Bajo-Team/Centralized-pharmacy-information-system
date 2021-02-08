package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import org.springframework.security.core.context.SecurityContextHolder;

public interface ISupplierService {

    Supplier registerSupplier(UserRegisterDTO supplier);

    Boolean existsByEmail(String email);

    Supplier getSupplierByUserAccount(UserAccount account);

    Supplier getLoggedSupplier();
}
