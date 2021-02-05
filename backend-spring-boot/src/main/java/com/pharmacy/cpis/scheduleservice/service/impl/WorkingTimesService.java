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
    public Boolean isConsultantWorkingTime(Long consultantID, Date examinationTime) {
        WorkingTimes consultantWorkingTime = consultantWorkingTime((long) 5);

        //number ranges from 1 (Sunday) to 7 (Saturday).
        Integer numberOfWeekDay = getDayNumberOld(examinationTime);
        if(numberOfWeekDay == 1){
            if(compareTimes(examinationTime, consultantWorkingTime.getSunday().getStart()) >=0 && compareTimes(examinationTime, consultantWorkingTime.getSunday().getEnd()) < 0){
                return true;
            }
        }else if(numberOfWeekDay == 2){
            if(compareTimes(examinationTime, consultantWorkingTime.getMonday().getStart()) >=0 && compareTimes(examinationTime, consultantWorkingTime.getMonday().getEnd()) < 0){
                return true;
            }
        }else if(numberOfWeekDay == 3){
            if(compareTimes(examinationTime, consultantWorkingTime.getTuesday().getStart()) >=0 && compareTimes(examinationTime, consultantWorkingTime.getTuesday().getEnd()) < 0){
                return true;
            }
        }else if(numberOfWeekDay == 4){
            if(compareTimes(examinationTime, consultantWorkingTime.getWednesday().getStart()) >=0 && compareTimes(examinationTime, consultantWorkingTime.getWednesday().getEnd()) < 0){
                return true;
            }
        }else if(numberOfWeekDay == 5){
            if(compareTimes(examinationTime, consultantWorkingTime.getThursday().getStart()) >=0 && compareTimes(examinationTime, consultantWorkingTime.getThursday().getEnd()) < 0){
                return true;
            }
        }else if(numberOfWeekDay == 6){
            if(compareTimes(examinationTime, consultantWorkingTime.getFriday().getStart()) >=0 && compareTimes(examinationTime, consultantWorkingTime.getFriday().getEnd()) < 0){
                return true;
            }
        }else if(numberOfWeekDay == 7){
            if(compareTimes(examinationTime, consultantWorkingTime.getSaturday().getStart()) >=0 && compareTimes(examinationTime, consultantWorkingTime.getSaturday().getEnd()) < 0){
                return true;
            }
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

