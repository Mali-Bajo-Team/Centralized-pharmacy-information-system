package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.*;
import com.pharmacy.cpis.userservice.repository.ISupplierRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IAuthorityService;
import com.pharmacy.cpis.userservice.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService implements ISupplierService {

    @Autowired
    private ISupplierRepository supplierRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthorityService authService;


    @Autowired
    private IUserRepository userRepository;


    // TODO: Think about how to improve DRY, because this is similar as patient, dermatologist registration service
    // only difference is that this is for supplier :/
    @Override
    public Supplier registerSupplier(UserRegisterDTO supplier) {
        Supplier addedDermatologist = addNewSupplier(supplier);
        UserAccount addedAccount = addNewSupplierAccount(supplier, addedDermatologist);
        return addedDermatologist;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserAccount addNewSupplierAccount(UserRegisterDTO userRequest, Supplier addedSupplier) {
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setEmail(userRequest.getEmail());
        newUserAccount.setPassword( passwordEncoder.encode(userRequest.getPassword()));
        newUserAccount.setActive(true);
        newUserAccount.setNeedsPasswordChange(true);
        List<Authority> auth = authService.findByName("ROLE_SUPPLIER");
        newUserAccount.setAuthorities(auth);
        newUserAccount.setPerson(addedSupplier);

        UserAccount addedAccount = userRepository.save(newUserAccount);
        return addedAccount;
    }

    private Supplier addNewSupplier(UserRegisterDTO userRequest) {
        Supplier newSupplier = new Supplier();
        newSupplier.setAddress(userRequest.getAddress());
        newSupplier.setCity(userRequest.getCity());
        newSupplier.setCountry(userRequest.getCountry());
        newSupplier.setName(userRequest.getName());
        newSupplier.setSurname(userRequest.getSurname());
        newSupplier.setPhoneNumber(userRequest.getMobile());
        Supplier addedSupplier = supplierRepository.save(newSupplier);
        return addedSupplier;
    }
}
