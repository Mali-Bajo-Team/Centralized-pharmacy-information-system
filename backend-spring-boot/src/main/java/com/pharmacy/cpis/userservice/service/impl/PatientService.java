package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private IPatientRepository patientRepository;

    @Override
    public List<Patient> findAllPatient() {
        return patientRepository.findAll();
    }
}
