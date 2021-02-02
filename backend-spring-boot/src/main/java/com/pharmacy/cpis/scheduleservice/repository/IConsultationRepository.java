package com.pharmacy.cpis.scheduleservice.repository;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsultationRepository  extends JpaRepository<Consultation, Long> {
}
