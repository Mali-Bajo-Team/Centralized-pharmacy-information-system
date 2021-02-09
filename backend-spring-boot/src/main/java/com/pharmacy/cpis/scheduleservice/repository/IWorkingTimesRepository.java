package com.pharmacy.cpis.scheduleservice.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;

public interface IWorkingTimesRepository extends JpaRepository<WorkingTimes, Long> {

	Optional<WorkingTimes> findByPharmacyIdAndConsultantId(Long pharmacyId, Long consultantId);

	Collection<WorkingTimes> findAllByPharmacyId(Long pharmacyId);

}
