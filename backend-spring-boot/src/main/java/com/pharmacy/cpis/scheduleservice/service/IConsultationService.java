package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.dto.PatientCancelConsultationDTO;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    // All pharmacies where that patient had consultation
    List<Pharmacy> findAllPatientPharmacies(Patient patient);

    Set<Consultation> findAllConsultationByPatientAndStatus(PatientEmailDTO patientEmailDTO, ConsultationStatus status, ConsultantType consultantType);

    void cancelConsultation(PatientCancelConsultationDTO patientCancelConsultationDTO);

}
