package com.pharmacy.cpis.scheduleservice.service;

import java.util.Date;
import java.util.List;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.util.DateRange;

public interface IWorkingTimesService {

	List<WorkingTimes> findAll();

	WorkingTimes consultantWorkingTime(Long consultantID);

	Pharmacy consultantWorkingPharmacy(Long consultantID);

	Boolean isConsultationTimeFitsIntoConsultantWorkingTime(Long consultantID, Date examinationStartDate,
			Date examinationTime);

	boolean consultationFitsIntoWorkingTime(Long consultantId, Long pharmacyId, DateRange consultationTime);

}
