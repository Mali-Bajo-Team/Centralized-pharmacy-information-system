package com.pharmacy.cpis.scheduleservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.userservice.model.users.Consultant;

public interface IConsultationRepository extends JpaRepository<Consultation, Long> {

	Collection<Consultation> findAllByConsultantAndStatus(Consultant consultant, ConsultationStatus status);

	Collection<Consultation> findAllByPharmacyIdAndStatus(Long pharmacyId, ConsultationStatus status);

}
