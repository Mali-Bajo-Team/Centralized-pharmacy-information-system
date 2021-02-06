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
            if(wt.getConsultant().getId() == consultantID){
                consultantWorkingTime = wt;
                return  consultantWorkingTime;
            }
        }
        return  consultantWorkingTime;
    }

    @Override
    public Boolean isConsultationTimeFitsIntoConsultantWorkingTime(Long consultantID, Date examinationStartDate, Date examinationEndDate) {
        WorkingTimes consultantWorkingTime = consultantWorkingTime(consultantID);

        //number ranges from 1 (Sunday) to 7 (Saturday).
        Integer numberOfWeekDay = getDayNumberOld(examinationStartDate);
        if(numberOfWeekDay == 1){
            return checkSunday(examinationStartDate, examinationEndDate, consultantWorkingTime);
        }else if(numberOfWeekDay == 2){
            return checkMonday(examinationStartDate, examinationEndDate, consultantWorkingTime);
        }else if(numberOfWeekDay == 3){
            return checkTuesday(examinationStartDate, examinationEndDate, consultantWorkingTime);
        }else if(numberOfWeekDay == 4){
            return checkWednesday(examinationStartDate, examinationEndDate, consultantWorkingTime);
        }else if(numberOfWeekDay == 5){
            return checkThursday(examinationStartDate, examinationEndDate, consultantWorkingTime);
        }else if(numberOfWeekDay == 6){
            return checkFriday(examinationStartDate, examinationEndDate, consultantWorkingTime);
        }else if(numberOfWeekDay == 7){
            return checkSaturday(examinationStartDate, examinationEndDate, consultantWorkingTime);
        }
        return false;
    }

    private boolean checkSunday(Date examinationStartDate, Date examinationEndDate, WorkingTimes consultantWorkingTime) {
        if(compareTimes(examinationStartDate, consultantWorkingTime.getSunday().getStart()) >=0
                && compareTimes(examinationStartDate, consultantWorkingTime.getSunday().getEnd()) < 0
                && compareTimes(examinationEndDate, examinationStartDate) > 0
                && compareTimes(examinationEndDate, consultantWorkingTime.getSunday().getEnd()) < 0){
            return true;
        }
        return false;
    }

    private boolean checkMonday(Date examinationStartDate, Date examinationEndDate, WorkingTimes consultantWorkingTime) {
        if(compareTimes(examinationStartDate, consultantWorkingTime.getMonday().getStart()) >=0
                && compareTimes(examinationStartDate, consultantWorkingTime.getMonday().getEnd()) < 0
                && compareTimes(examinationEndDate, examinationStartDate) > 0
                && compareTimes(examinationEndDate, consultantWorkingTime.getMonday().getEnd()) < 0){
            return true;
        }
        return false;
    }

    private boolean checkTuesday(Date examinationStartDate, Date examinationEndDate, WorkingTimes consultantWorkingTime) {
        if(compareTimes(examinationStartDate, consultantWorkingTime.getTuesday().getStart()) >=0
                && compareTimes(examinationStartDate, consultantWorkingTime.getTuesday().getEnd()) < 0
                && compareTimes(examinationEndDate, examinationStartDate) > 0
                && compareTimes(examinationEndDate, consultantWorkingTime.getTuesday().getEnd()) < 0){
            return true;
        }
        return false;
    }
    private boolean checkWednesday(Date examinationStartDate, Date examinationEndDate, WorkingTimes consultantWorkingTime) {
        if(compareTimes(examinationStartDate, consultantWorkingTime.getWednesday().getStart()) >=0
                && compareTimes(examinationStartDate, consultantWorkingTime.getWednesday().getEnd()) < 0
                && compareTimes(examinationEndDate, examinationStartDate) > 0
                && compareTimes(examinationEndDate, consultantWorkingTime.getWednesday().getEnd()) < 0){
            return true;
        }
        return false;
    }
    private boolean checkThursday(Date examinationStartDate, Date examinationEndDate, WorkingTimes consultantWorkingTime) {
        if(compareTimes(examinationStartDate, consultantWorkingTime.getThursday().getStart()) >=0
                && compareTimes(examinationStartDate, consultantWorkingTime.getThursday().getEnd()) < 0
                && compareTimes(examinationEndDate, examinationStartDate) > 0
                && compareTimes(examinationEndDate, consultantWorkingTime.getThursday().getEnd()) < 0){
            return true;
        }
        return false;
    }
    private boolean checkFriday(Date examinationStartDate, Date examinationEndDate, WorkingTimes consultantWorkingTime) {
        if(compareTimes(examinationStartDate, consultantWorkingTime.getFriday().getStart()) >=0
                && compareTimes(examinationStartDate, consultantWorkingTime.getFriday().getEnd()) < 0
                && compareTimes(examinationEndDate, examinationStartDate) > 0
                && compareTimes(examinationEndDate, consultantWorkingTime.getFriday().getEnd()) < 0){
            return true;
        }
        return false;
    }
    private boolean checkSaturday(Date examinationStartDate, Date examinationEndDate, WorkingTimes consultantWorkingTime) {
        if(compareTimes(examinationStartDate, consultantWorkingTime.getSaturday().getStart()) >=0
                && compareTimes(examinationStartDate, consultantWorkingTime.getSaturday().getEnd()) < 0
                && compareTimes(examinationEndDate, examinationStartDate) > 0
                && compareTimes(examinationEndDate, consultantWorkingTime.getSaturday().getEnd()) < 0){
            return true;
        }
        return false;
    }
    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        System.out.println("DAN U NEDELJI KONVERTER : " +cal.get(Calendar.DAY_OF_WEEK) );
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public int compareTimes(Date d1, Date d2)
    {
        int     t1;
        int     t2;

        t1 = (int) (d1.getTime() % (24*60*60*1000L));
        t2 = (int) (d2.getTime() % (24*60*60*1000L));
        return (t1 - t2);
    }
}

