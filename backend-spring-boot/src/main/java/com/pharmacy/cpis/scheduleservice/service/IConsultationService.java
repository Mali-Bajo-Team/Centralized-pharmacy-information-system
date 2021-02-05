package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;

import java.util.List;

public interface IConsultationService {
    List<Consultation> findAll();


    Consultation scheduleConsultation(Consultation consultation);
}
