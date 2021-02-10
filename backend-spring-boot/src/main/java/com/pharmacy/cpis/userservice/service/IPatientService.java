package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.model.users.Patient;

import java.util.List;
import java.util.Set;

public interface IPatientService {
    List<Patient> findAllPatient();

    Patient findByEmail(String email);

    Patient addPenaltie(String email, Long consultationID);

    Set<Pharmacy> findPatientSubscriptions(String patientEmail);

}
