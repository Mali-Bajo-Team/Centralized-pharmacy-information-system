package com.pharmacy.cpis.userservice.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.userservice.dto.AddWorkingDayDTO;
import com.pharmacy.cpis.userservice.dto.AddWorkingTimeDTO;
import com.pharmacy.cpis.userservice.dto.EmployDermatologistDTO;
import com.pharmacy.cpis.userservice.dto.EmployPharmacistDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.IPharmacyEmployeeService;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.DateRange;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class PharmacyEmployeeService implements IPharmacyEmployeeService {

	@Autowired
	private IWorkingTimesRepository workingTimesRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IConsultationRepository consultationRepository;

	@Autowired
	private IConsultantRepository consultantRepository;

	@Autowired
	private IConsultantService consultantService;

	@Autowired
	private IPharmacyRepository pharmacyRepository;

	@Override
	@Transactional
	public WorkingTimes employPharmacist(Long pharmacyId, EmployPharmacistDTO details) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);
		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		Consultant consultant = consultantService.registerConsultant(details.getPharmacist(),
				ConsultantType.PHARMACIST);

		WorkingTimes workingTimes = initializeWorkingTimes(consultant, pharmacy, details.getWorkingTimes());

		return workingTimesRepository.save(workingTimes);
	}

	@Override
	@Transactional
	public WorkingTimes employDermatologist(Long pharmacyId, EmployDermatologistDTO details) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);
		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		Consultant consultant = consultantRepository.findLockedById(details.getDermatologistId()).orElse(null);
		if (consultant == null || !consultant.getType().equals(ConsultantType.DERMATOLOGIST))
			throw new PSNotFoundException("The requested dermatologist does not exist.");

		if (!doesntWorkInPharmacy(consultant, pharmacyId))
			throw new PSConflictException("The consultant already works for the pharmacy.");

		WorkingTimes workingTimes = initializeWorkingTimes(consultant, pharmacy, details.getWorkingTimes());

		if (CollectionUtil.contains(consultant.getWorkingTimes(), wt -> workingTimesInConflict(workingTimes, wt)))
			throw new PSConflictException("Working times overlap with working times in another pharmacy.");

		return workingTimesRepository.save(workingTimes);
	}

	private WorkingTimes initializeWorkingTimes(Consultant consultant, Pharmacy pharmacy, AddWorkingTimeDTO wt) {
		WorkingTimes initialized = new WorkingTimes();
		initialized.setConsultant(consultant);
		initialized.setPharmacy(pharmacy);

		if (wt.getMonday() == null && wt.getTuesday() == null && wt.getWednesday() == null && wt.getThursday() == null
				&& wt.getFriday() == null && wt.getSaturday() == null && wt.getSunday() == null)
			throw new PSBadRequestException("Working time must have at least one working day.");

		initialized.setMonday(initializeWorkingDay(wt.getMonday()));
		initialized.setTuesday(initializeWorkingDay(wt.getTuesday()));
		initialized.setWednesday(initializeWorkingDay(wt.getWednesday()));
		initialized.setThursday(initializeWorkingDay(wt.getThursday()));
		initialized.setFriday(initializeWorkingDay(wt.getFriday()));
		initialized.setSaturday(initializeWorkingDay(wt.getSaturday()));
		initialized.setSunday(initializeWorkingDay(wt.getSunday()));

		return initialized;
	}

	private DateRange initializeWorkingDay(AddWorkingDayDTO wd) {
		if (wd == null)
			return null;

		DateRange initialized = new DateRange();
		initialized.setStart(DateConversionsAndComparisons.getTime(wd.getStart()));
		initialized.setEnd(DateConversionsAndComparisons.getTime(wd.getEnd()));

		if (DateConversionsAndComparisons.compareTimesWithoutDates(initialized.getStart(), initialized.getEnd()) >= 0)
			throw new PSBadRequestException("Shift start must be before the shift end.");

		return initialized;
	}

	private boolean workingTimesInConflict(WorkingTimes wt1, WorkingTimes wt2) {
		return workingDayInConflict(wt1.getMonday(), wt2.getMonday())
				|| workingDayInConflict(wt1.getTuesday(), wt2.getTuesday())
				|| workingDayInConflict(wt1.getWednesday(), wt2.getWednesday())
				|| workingDayInConflict(wt1.getThursday(), wt2.getThursday())
				|| workingDayInConflict(wt1.getFriday(), wt2.getFriday())
				|| workingDayInConflict(wt1.getSaturday(), wt2.getSaturday())
				|| workingDayInConflict(wt1.getSunday(), wt2.getSunday());
	}

	private boolean workingDayInConflict(DateRange wd1, DateRange wd2) {
		if (wd1 == null || wd2 == null)
			return false;

		return DateConversionsAndComparisons.overlapsWithoutDates(wd1, wd2);
	}

	@Override
	public Collection<Consultant> getDermatologistsWhoDontWorkInPharmacy(Long pharmacyId) {
		Collection<Consultant> dermatologists = consultantService.getByType(ConsultantType.DERMATOLOGIST);

		return CollectionUtil.findAll(dermatologists, dermatologist -> doesntWorkInPharmacy(dermatologist, pharmacyId));
	}

	private boolean doesntWorkInPharmacy(Consultant consultant, Long pharmacyId) {
		return !CollectionUtil.contains(consultant.getWorkingTimes(),
				wt -> wt.getPharmacy().getId().equals(pharmacyId));
	}

	@Override
	@Transactional
	public void fireConsultant(Long pharmacyId, Long consultantId) {
		WorkingTimes employment = workingTimesRepository.findByPharmacyIdAndConsultantId(pharmacyId, consultantId)
				.orElse(null);

		if (employment == null)
			throw new PSBadRequestException("The requested consultant doesn't work for the requested pharmacy.");

		if (hasUnfinishedConsultations(employment.getConsultant(), pharmacyId))
			throw new PSConflictException("The consultant still has unfinished consultations and cannot be fired.");

		if (employment.getConsultant().getType().equals(ConsultantType.PHARMACIST))
			userRepository.delete(employment.getConsultant().getAccount());

		workingTimesRepository.delete(employment);
	}

	private boolean hasUnfinishedConsultations(Consultant consultant, Long pharmacyId) {
		if (!consultationRepository
				.findAllByPharmacyIdAndConsultantAndStatus(pharmacyId, consultant, ConsultationStatus.SCHEDULED)
				.isEmpty())
			return true;
		if (!consultationRepository
				.findAllByPharmacyIdAndConsultantAndStatus(pharmacyId, consultant, ConsultationStatus.PREDEFINED)
				.isEmpty())
			return true;

		return false;
	}

}
