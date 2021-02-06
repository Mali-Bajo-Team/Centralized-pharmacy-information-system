package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ConsultationService implements IConsultationService {

    @Autowired
    private IConsultationRepository consultationRepository;

    @Autowired
    private IConsultantRepository consultantRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IWorkingTimesService workingTimesService;

    @Override
    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }

    @Override
    public Boolean isPhatientHaveConsultation(Long patientId, Date examinationStartDate, Date examinationEndDate) {
        Patient patient = patientRepository.getOne(patientId);

        for (Consultation c: patient.getConsultations()) {
            //ESD izmedju CSD i CED
            if(DateConversionsAndComparisons.compareDates(c.getTime().getStart(), examinationStartDate) <=0 && DateConversionsAndComparisons.compareDates(c.getTime().getEnd(), examinationStartDate) >=0 ){ return true; }
            //EED izmedju CSD i CED
            if(DateConversionsAndComparisons.compareDates(c.getTime().getEnd(),examinationEndDate) >=0 && DateConversionsAndComparisons.compareDates(c.getTime().getStart(), examinationEndDate) <=0){ return true; }
        }

        return false;
    }

    @Override
    public Consultation scheduleConsultation(ScheduleExaminationDTO consultation) {

        DateRange consultationDataRange = new DateRange();
        consultationDataRange.setStart(DateConversionsAndComparisons.getUtilDate(consultation.getStartDate()));
        consultationDataRange.setEnd(DateConversionsAndComparisons.getUtilDate(consultation.getEndDate()));

        Consultant consultant = consultantRepository.getOne(consultation.getConsultantId());
        Pharmacy consultantWorkingPharmacy = workingTimesService.consultantWorkingPharmacy(consultant.getId());

        Consultation consultationForSchedule = new Consultation();
        consultationForSchedule.setConsultant(consultant);
        consultationForSchedule.setPatient(patientRepository.getOne(consultation.getPatientId()));
        consultationForSchedule.setPharmacy(consultantWorkingPharmacy);
        consultationForSchedule.setPrice(consultantWorkingPharmacy.getPharmacistConsultationPrice());
        consultationForSchedule.setStatus(ConsultationStatus.SCHEDULED);
        consultationForSchedule.setTime(consultationDataRange);

        consultationRepository.save(consultationForSchedule);

        return  consultationForSchedule;
    }

}
