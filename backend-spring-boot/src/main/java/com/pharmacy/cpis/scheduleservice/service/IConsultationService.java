package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

import java.util.Date;
import java.util.List;

public interface IConsultationService {
    List<Consultation> findAll();

    Consultation scheduleConsultation(ScheduleExaminationDTO consultation);

    Boolean isPhatientHaveConsultation(Long patientId, Date examinationStartDate, Date examinationEndDate);
}
