package com.pharmacy.cpis.scheduleservice.repository;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVacationRequestRepository  extends JpaRepository<VacationRequest, Long> {

}
