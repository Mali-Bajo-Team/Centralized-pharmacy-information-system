package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

import java.util.Date;
import java.util.List;

public interface IConsultationService {
    List<Consultation> findAll();

    Boolean isPhatientFreeForConsultation(Long patientId, Date examinationStartDate, Date examinationEndDate);

    Consultation scheduleConsultation(ScheduleExaminationDTO consultation);

}
