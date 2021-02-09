package com.pharmacy.cpis.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IPharmacyEmployeeService;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSConflictException;

@Service
public class PharmacyEmployeeService implements IPharmacyEmployeeService {

	@Autowired
	private IWorkingTimesRepository workingTimesRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IConsultationRepository consultationRepository;

	@Override
	public void fireConsultant(Long pharmacyId, Long consultantId) {
		WorkingTimes employment = workingTimesRepository.findByPharmacyIdAndConsultantId(pharmacyId, consultantId)
				.orElse(null);

		if (employment == null)
			throw new PSBadRequestException("The requested consultant doesn't work for the requested pharmacy.");

		if (hasUnfinishedConsultations(employment.getConsultant()))
			throw new PSConflictException("The consultant still has unfinished consultations and cannot be fired.");

		if (employment.getConsultant().getType().equals(ConsultantType.PHARMACIST))
			userRepository.delete(employment.getConsultant().getAccount());

		workingTimesRepository.delete(employment);
	}

	private boolean hasUnfinishedConsultations(Consultant consultant) {
		if (!consultationRepository.findAllByConsultantAndStatus(consultant, ConsultationStatus.SCHEDULED).isEmpty())
			return true;
		if (!consultationRepository.findAllByConsultantAndStatus(consultant, ConsultationStatus.PREDEFINED).isEmpty())
			return true;

		return false;
	}

}
