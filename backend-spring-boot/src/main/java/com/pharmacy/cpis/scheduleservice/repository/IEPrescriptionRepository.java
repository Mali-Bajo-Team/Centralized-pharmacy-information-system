package com.pharmacy.cpis.scheduleservice.repository;

import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescriptionStatus;
import com.pharmacy.cpis.userservice.model.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEPrescriptionRepository extends JpaRepository<EPrescription, Long> {
    List<EPrescription> findAllByPatientId(Long patientId);
    List<EPrescription> findAllByStatusAndPatientId(EPrescriptionStatus status, Long patientId);
}
