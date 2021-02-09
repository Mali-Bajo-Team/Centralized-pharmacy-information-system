package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WorkingTimesService implements IWorkingTimesService {
    @Autowired
    private IWorkingTimesRepository workingTimesRepository;

    @Override
    public List<WorkingTimes> findAll() {
       return workingTimesRepository.findAll();
    }

    @Override
    public WorkingTimes consultantWorkingTime(Long consultantID) {
        WorkingTimes consultantWorkingTime = new WorkingTimes();
        List<WorkingTimes> workingTimes = workingTimesRepository.findAll();
        for (WorkingTimes wt : workingTimes) {
            if(wt.getConsultant().getId().equals(consultantID)){
                consultantWorkingTime = wt;
                return  consultantWorkingTime;
            }
        }
        return  consultantWorkingTime;
    }

    @Override
    public Pharmacy consultantWorkingPharmacy(Long consultantID) {
        Pharmacy consultantWorkingPharmacy = new Pharmacy();
        List<WorkingTimes> workingTimes = workingTimesRepository.findAll();
        for (WorkingTimes wt : workingTimes) {
            if(wt.getConsultant().getId().equals(consultantID)){
                consultantWorkingPharmacy = wt.getPharmacy();
                return  consultantWorkingPharmacy;
            }
        }
        return  consultantWorkingPharmacy;
    }

    @Override
    public List<Pharmacy> dermatologistWorkingPharmacies(Long consultantID) {
        List<Pharmacy> dermatlogistWorkingPharmacy = new ArrayList<>();
        List<WorkingTimes> workingTimes = workingTimesRepository.findAll();

        for (WorkingTimes wt : workingTimes) {
            if(wt.getConsultant().getId().equals(consultantID)){
                dermatlogistWorkingPharmacy.add(wt.getPharmacy());
                return  dermatlogistWorkingPharmacy;
            }
        }
        return  dermatlogistWorkingPharmacy;
    }

    @Override
    public Boolean isConsultationTimeFitsIntoConsultantWorkingTime(Long consultantID, Date examinationStartDate, Date examinationEndDate) {
        WorkingTimes consultantWorkingTime = consultantWorkingTime(consultantID);

        Integer numberOfWeekDay = DateConversionsAndComparisons.getDayNumberOld(examinationStartDate);
        if(numberOfWeekDay == 1){
            return checkDay(examinationStartDate, examinationEndDate, consultantWorkingTime, consultantWorkingTime.getSunday());
        }else if(numberOfWeekDay == 2){
            return checkDay(examinationStartDate, examinationEndDate, consultantWorkingTime, consultantWorkingTime.getMonday());
        }else if(numberOfWeekDay == 3){
            return checkDay(examinationStartDate, examinationEndDate, consultantWorkingTime, consultantWorkingTime.getTuesday());
        }else if(numberOfWeekDay == 4){
            return checkDay(examinationStartDate, examinationEndDate, consultantWorkingTime, consultantWorkingTime.getWednesday());
        }else if(numberOfWeekDay == 5){
            return checkDay(examinationStartDate, examinationEndDate, consultantWorkingTime, consultantWorkingTime.getThursday());
        }else if(numberOfWeekDay == 6){
            return checkDay(examinationStartDate, examinationEndDate, consultantWorkingTime, consultantWorkingTime.getFriday());
        }else if(numberOfWeekDay == 7){
            return checkDay(examinationStartDate, examinationEndDate, consultantWorkingTime, consultantWorkingTime.getSaturday());
        }
        return false;
    }

    private boolean checkDay(Date examinationStartDate, Date examinationEndDate, WorkingTimes consultantWorkingTime, DateRange dayForCheck) {
        if(DateConversionsAndComparisons.compareTimesWithoutDates(examinationStartDate, dayForCheck.getStart()) >=0
                && DateConversionsAndComparisons.compareTimesWithoutDates(examinationStartDate, dayForCheck.getEnd()) < 0
                && DateConversionsAndComparisons.compareTimesWithoutDates(examinationEndDate, examinationStartDate) > 0
                && DateConversionsAndComparisons.compareTimesWithoutDates(examinationEndDate, dayForCheck.getEnd()) < 0){
            return true;
        }
        return false;
    }


}

