package com.pharmacy.cpis.scheduleservice.repository;

import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEPrescriptionRepository extends JpaRepository<EPrescription, Long> {
}
