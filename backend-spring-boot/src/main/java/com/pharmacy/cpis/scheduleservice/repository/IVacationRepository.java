package com.pharmacy.cpis.scheduleservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.scheduleservice.model.workschedule.Vacation;

public interface IVacationRepository extends JpaRepository<Vacation, Long> {

}
