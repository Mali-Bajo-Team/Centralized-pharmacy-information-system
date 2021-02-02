package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.model.users.Patient;

public interface IPatientService {
    Patient save(Patient newPatient);
}
