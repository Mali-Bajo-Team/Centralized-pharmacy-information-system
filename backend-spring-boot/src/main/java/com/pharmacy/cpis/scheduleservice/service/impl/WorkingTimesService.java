package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Boolean isConsultantWorkingTime(Long consultantID, Date examinationTime) {
        WorkingTimes consultantWorkingTime = consultantWorkingTime(consultantID);

        //number ranges from 1 (Sunday) to 7 (Saturday).
        Integer numberOfWeekDay = getDayNumberOld(examinationTime);
        if(numberOfWeekDay == 1){
            if(examinationTime.getTime() >= consultantWorkingTime.getSunday().getStart().getTime() && examinationTime.getTime() < consultantWorkingTime.getSunday().getEnd().getTime()){
                return true;
            }
        }else if(numberOfWeekDay == 2){
            if(examinationTime.getTime() >= consultantWorkingTime.getMonday().getStart().getTime() && examinationTime.getTime() < consultantWorkingTime.getMonday().getEnd().getTime()){
                return true;
            }
        }else if(numberOfWeekDay == 3){
            if(examinationTime.getTime() >= consultantWorkingTime.getTuesday().getStart().getTime() && examinationTime.getTime() < consultantWorkingTime.getTuesday().getEnd().getTime()){
                return true;
            }
        }else if(numberOfWeekDay == 4){
            if(examinationTime.getTime() >= consultantWorkingTime.getWednesday().getStart().getTime() && examinationTime.getTime() < consultantWorkingTime.getWednesday().getEnd().getTime()){
                return true;
            }
        }else if(numberOfWeekDay == 5){
            if(examinationTime.getTime() >= consultantWorkingTime.getThursday().getStart().getTime() && examinationTime.getTime() < consultantWorkingTime.getThursday().getEnd().getTime()){
                return true;
            }
        }else if(numberOfWeekDay == 6){
            if(examinationTime.getTime() >= consultantWorkingTime.getFriday().getStart().getTime() && examinationTime.getTime() < consultantWorkingTime.getFriday().getEnd().getTime()){
                return true;
            }
        }else if(numberOfWeekDay == 7){
            if(examinationTime.getTime() >= consultantWorkingTime.getSaturday().getStart().getTime() && examinationTime.getTime() < consultantWorkingTime.getSaturday().getEnd().getTime()){
                return true;
            }
        }
        return false;
    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}

