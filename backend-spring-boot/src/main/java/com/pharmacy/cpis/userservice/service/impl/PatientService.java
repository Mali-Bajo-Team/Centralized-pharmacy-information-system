package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IUserRepository userAccountRepository;

    @Override
    public List<Patient> findAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findByEmail(String email){
         UserAccount userAccount= userAccountRepository.findByEmail(email);
         return patientRepository.findById(userAccount.getPerson().getId()).orElse(null);
    }

    @Override
    public Set<Pharmacy> findPatientSubscriptions(String patientEmail) {
        Patient patient = findByEmail(patientEmail);
        return patient.getSubscriptions();
    }
}
