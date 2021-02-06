package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;

import java.util.Date;
import java.util.List;

public interface IConsultationService {
    List<Consultation> findAll();

    Consultation scheduleConsultation(Consultation consultation);

    Boolean isPhatientHaveConsultation(Long patientId, Date examinationStartDate, Date examinationEndDate);
}
