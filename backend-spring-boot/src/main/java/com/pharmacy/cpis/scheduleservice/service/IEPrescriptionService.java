package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.scheduleservice.dto.EPrescriptionCreateDTO;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescriptionStatus;
import com.pharmacy.cpis.userservice.model.users.Patient;

import java.util.List;

public interface IEPrescriptionService {
    List<EPrescription> findAllPatientEPrescription(Long patientId);
    List<EPrescription> findAllPatientEPrescriptionByStatus(Long patientId, EPrescriptionStatus status);
    EPrescription savePrescription(EPrescriptionCreateDTO prescriptionDTO);
}
