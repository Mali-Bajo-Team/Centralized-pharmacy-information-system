package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ConsultationService implements IConsultationService {

    @Autowired
    private IConsultationRepository consultationRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Override
    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }

    @Override
    public Boolean isPhatientHaveConsultation(Long patientId, Date examinationStartDate, Date examinationEndDate) {
        Patient patient = patientRepository.getOne(patientId);

        System.out.println(patient.getName());
        return true;
    }

    @Override
    public Consultation scheduleConsultation(Consultation consultation) {
        return consultation;
    }


}
