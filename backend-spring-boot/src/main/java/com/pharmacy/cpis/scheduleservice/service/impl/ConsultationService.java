package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConsultationService implements IConsultationService {

    @Autowired
    private IConsultationRepository consultationRepository;

    @Override
    public List<Consultation> findAll() {
        return consultationRepository.findAll();
    }

//    @Override
//    public Boolean isConsultationTimeFitsIntoConsultantWorkingTime(){
//
//    }

//. Satnica mora da se poklapa sa radnim vremenom farmaceuta. Moram prvo da dobavim radno vreme tog farmaceuta
    @Override
    public Consultation scheduleConsultation(Consultation consultation) {

        return consultation;
    }
}
