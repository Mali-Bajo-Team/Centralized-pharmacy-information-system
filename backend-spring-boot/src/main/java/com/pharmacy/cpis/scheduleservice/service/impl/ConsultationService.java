package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.util.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public Consultation scheduleConsultation(ScheduleExaminationDTO consultation) {
        Date examinationStartDate = getUtilDate(consultation.getStartDate());
        Date examinationEndDate = getUtilDate(consultation.getEndDate());
        DateRange consultationDataRange = new DateRange();
        consultationDataRange.setStart(examinationStartDate);
        consultationDataRange.setEnd(examinationEndDate);

        Consultation consultationForSchedule = new Consultation();
        Consultant consultant = new Consultant();
        consultant = consultantRepository.getOne(consultation.getConsultantId());
        Pharmacy consultantWorkingPharmacy = workingTimesService.consultantWorkingPharmacy(consultant.getId());

        consultationForSchedule.setConsultant(consultant);
        consultationForSchedule.setPatient(patientRepository.getOne(consultation.getPatientId()));
        consultationForSchedule.setPharmacy(consultantWorkingPharmacy);
        consultationForSchedule.setPrice(consultantWorkingPharmacy.getPharmacistConsultationPrice());
        consultationForSchedule.setStatus(ConsultationStatus.SCHEDULED);
        consultationForSchedule.setTime(consultationDataRange);

        consultationRepository.save(consultationForSchedule);

        return  consultationForSchedule;
    }
    private Date getUtilDate(String stringTime) {
        Date convertedUtilDate = null;
        try {
            convertedUtilDate=new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(stringTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedUtilDate;
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
