package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.*;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IAuthorityService;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultantService implements IConsultantService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthorityService authService;

    @Autowired
    private IConsultantRepository consultantRepository;

    @Autowired
    private IUserRepository userRepository;


    // TODO: Think about how to improve DRY, because this is similar as patient registration service
    // only difference is that this is for dermatologist :/
    @Override
    public Consultant registerDermatologist(UserRegisterDTO dermatologist) {
        Consultant addedDermatologist = addNewDermatologist(dermatologist);
        UserAccount addedAccount = addNewDermatologistAccount(dermatologist, addedDermatologist);
        return addedDermatologist;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserAccount addNewDermatologistAccount(UserRegisterDTO userRequest, Consultant addedConsultant) {
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setEmail(userRequest.getEmail());
        newUserAccount.setPassword( passwordEncoder.encode(userRequest.getPassword()));
        newUserAccount.setActive(false);
        List<Authority> auth = authService.findByName("ROLE_DERMATOLOGIST");
        newUserAccount.setAuthorities(auth);
        newUserAccount.setPerson(addedConsultant);

        UserAccount addedAccount = userRepository.save(newUserAccount);
        return addedAccount;
    }

    private Consultant addNewDermatologist(UserRegisterDTO userRequest) {
        Consultant newDermatologist = new Consultant();
        newDermatologist.setType(ConsultantType.DERMATOLOGIST);
        newDermatologist.setAddress(userRequest.getAddress());
        newDermatologist.setCity(userRequest.getCity());
        newDermatologist.setCountry(userRequest.getCountry());
        newDermatologist.setName(userRequest.getName());
        newDermatologist.setSurname(userRequest.getSurname());
        newDermatologist.setPhoneNumber(userRequest.getMobile());
        Consultant addedDermatologist = consultantRepository.save(newDermatologist);
        return addedDermatologist;
    }
}
