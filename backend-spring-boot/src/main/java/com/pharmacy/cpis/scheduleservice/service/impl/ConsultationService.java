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

        //Proci kroz sva vremena u konsultacijama i uporediti ih sa prosledjenim vremenima
        for (Consultation c: patient.getConsultations()) {
            //ESD izmedju CSD i CED
            if(compareDates(c.getTime().getStart(), examinationStartDate) <=0 && compareDates(c.getTime().getEnd(), examinationStartDate) >=0 ){ return true; }
            //EED izmedju CSD i CED
            if(compareDates(c.getTime().getEnd(),examinationEndDate) >=0 && compareDates(c.getTime().getStart(), examinationEndDate) <=0){ return true; }
        }

        return false;
    }

    @Override
    public Consultation scheduleConsultation(Consultation consultation) {
        return consultation;
    }

    //d1>d2 => >0
    //d1<d2 => <0
    //d1==d2 => =0
    public int compareDates(Date d1, Date d2)
    {
        int t1;
        int t2;

        t1 = (int) (d1.getTime());
        t2 = (int) (d2.getTime());
        System.out.println(t1-t2);
        return (t1 - t2);
    }
}
