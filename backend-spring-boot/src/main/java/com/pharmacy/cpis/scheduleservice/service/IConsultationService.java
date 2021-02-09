package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

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

    List<Consultant> findAllPatientConsultants(Patient patient);

    List<Pharmacy> findAllPatientPharmacies(Patient patient);

}
