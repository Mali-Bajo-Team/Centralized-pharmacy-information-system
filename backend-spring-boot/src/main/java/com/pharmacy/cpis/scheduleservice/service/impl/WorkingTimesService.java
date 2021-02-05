package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

