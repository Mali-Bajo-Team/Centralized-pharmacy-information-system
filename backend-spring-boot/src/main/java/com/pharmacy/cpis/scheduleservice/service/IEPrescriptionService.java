package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.scheduleservice.dto.prescription.EPrescriptionCreateDTO;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescriptionStatus;

import java.util.List;

public interface IEPrescriptionService {
    List<EPrescription> findAllPatientEPrescription(String patientEmail);
    List<EPrescription> findAllPatientEPrescriptionByStatus(Long patientId, EPrescriptionStatus status);
    EPrescription savePrescription(EPrescriptionCreateDTO prescriptionDTO);
}
