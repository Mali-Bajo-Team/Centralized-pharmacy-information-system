package com.pharmacy.cpis.scheduleservice.repository;

import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkingTimesRepository extends JpaRepository<WorkingTimes, Long> {
}
