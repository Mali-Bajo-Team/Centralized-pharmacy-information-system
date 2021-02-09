package com.pharmacy.cpis.scheduleservice.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.pharmacy.cpis.scheduleservice.dto.AddPredefinedConsultationDTO;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;

public interface IConsultationService {

	List<Consultation> findAll();

	Boolean isPhatientFreeForConsultation(Long patientId, Date examinationStartDate, Date examinationEndDate);

	Consultation scheduleConsultation(ScheduleExaminationDTO consultation);

	Consultation addPredefined(Long pharmacyId, AddPredefinedConsultationDTO consultationInfo);

	Collection<Consultation> findByPharmacyAndStatus(Long pharmacyId, ConsultationStatus status);

}
