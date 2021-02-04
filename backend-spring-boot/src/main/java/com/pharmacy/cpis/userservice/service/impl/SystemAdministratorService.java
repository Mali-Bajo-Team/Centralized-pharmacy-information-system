package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.SystemAdministrator;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.ISystemAdministratorRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IAuthorityService;
import com.pharmacy.cpis.userservice.service.ISystemAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemAdministratorService implements ISystemAdministratorService {

    @Autowired
    private ISystemAdministratorRepository systemAdministratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthorityService authService;

    @Autowired
    private IUserRepository userRepository;

    // TODO: Think about how to improve DRY, because this is similar as patient, dermatologist, supplier registration service
    // only difference is that this is for system administrator :/
    @Override
    public SystemAdministrator registerSystemAdministrator(UserRegisterDTO systemAdministrator) {
        SystemAdministrator addedDermatologist = addNewSupplier(systemAdministrator);
        UserAccount addedAccount = addNewSystemAdministratorAccount(systemAdministrator, addedDermatologist);
        return addedDermatologist;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    private UserAccount addNewSystemAdministratorAccount(UserRegisterDTO userRequest, SystemAdministrator addedAdministrator) {
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setEmail(userRequest.getEmail());
        newUserAccount.setPassword( passwordEncoder.encode(userRequest.getPassword()));
        newUserAccount.setActive(true);
        newUserAccount.setNeedsPasswordChange(true);
        List<Authority> auth = authService.findByName("ROLE_ADMIN");
        newUserAccount.setAuthorities(auth);
        newUserAccount.setPerson(addedAdministrator);

        UserAccount addedAccount = userRepository.save(newUserAccount);
        return addedAccount;
    }

    private SystemAdministrator addNewSupplier(UserRegisterDTO userRequest) {
        SystemAdministrator systemAdministrator = new SystemAdministrator();
        systemAdministrator.setAddress(userRequest.getAddress());
        systemAdministrator.setCity(userRequest.getCity());
        systemAdministrator.setCountry(userRequest.getCountry());
        systemAdministrator.setName(userRequest.getName());
        systemAdministrator.setSurname(userRequest.getSurname());
        systemAdministrator.setPhoneNumber(userRequest.getMobile());
        SystemAdministrator addedSystemAdministrator = systemAdministratorRepository.save(systemAdministrator);
        return addedSystemAdministrator;
    }
}
