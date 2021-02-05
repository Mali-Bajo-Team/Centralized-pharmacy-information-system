package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;

import java.util.List;

public interface IWorkingTimesService {
    List<WorkingTimes> findAll();
}
