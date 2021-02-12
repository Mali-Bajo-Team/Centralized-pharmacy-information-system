package com.pharmacy.cpis.scheduleservice.service.impl;

import java.util.*;

import com.pharmacy.cpis.scheduleservice.dto.PatientCancelConsultationDTO;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.scheduleservice.dto.AddPredefinedConsultationDTO;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.DateRange;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class ConsultationService implements IConsultationService {

	@Autowired
	private IConsultationRepository consultationRepository;

	@Autowired
	private IConsultantRepository consultantRepository;

	@Autowired
	private IPatientRepository patientRepository;

	@Autowired
	private IWorkingTimesService workingTimesService;

	@Autowired
	private IPharmacyRepository pharmacyRepository;

	@Autowired
	private IUserRepository userRepository;

	@Override
	public Collection<Consultation> findByPharmacyAndStatus(Long pharmacyId, ConsultationStatus status) {
		return consultationRepository.findAllByPharmacyIdAndStatus(pharmacyId, status);
	}

	@Override
	public List<Consultation> findAll() {
		return consultationRepository.findAll();
	}

	@Override
	public Boolean isPhatientFreeForConsultation(Long patientId, Date examinationStartDate, Date examinationEndDate) {
		Patient patient = patientRepository.getOne(patientId);

		for (Consultation c : patient.getConsultations()) {
			// ESD izmedju CSD i CED
			// EED izmedju CSD i CED
			if (DateConversionsAndComparisons.compareDates(c.getTime().getStart(), examinationStartDate) <= 0
					&& DateConversionsAndComparisons.compareDates(c.getTime().getEnd(), examinationStartDate) >= 0
					&& DateConversionsAndComparisons.compareDates(c.getTime().getEnd(), examinationEndDate) >= 0
					&& DateConversionsAndComparisons.compareDates(c.getTime().getStart(), examinationEndDate) <= 0) {
				return false;
			}
		}

		return true;
	}

	@Override
	@Transactional
	public Consultation scheduleConsultation(ScheduleExaminationDTO consultation) {

		DateRange consultationDataRange = new DateRange();
		consultationDataRange.setStart(DateConversionsAndComparisons.getUtilDate(consultation.getStartDate()));
		consultationDataRange.setEnd(DateConversionsAndComparisons.getUtilDate(consultation.getEndDate()));

		Consultant consultant = consultantRepository.getOne(consultation.getConsultantId());
		Pharmacy consultantWorkingPharmacy;

		if (Objects.isNull(consultation.getPharmacyID())) {
			consultantWorkingPharmacy = workingTimesService.consultantWorkingPharmacy(consultant.getId());
		} else {
			consultantWorkingPharmacy = pharmacyRepository.getOne(consultation.getPharmacyID());
		}
		Consultation consultationForSchedule;
		if(consultation.getPredefinedConsultationID()  == null) {
			consultationForSchedule = new Consultation();
			consultationForSchedule.setConsultant(consultant);
			consultationForSchedule.setPatient(patientRepository.getOne(consultation.getPatientId()));
			consultationForSchedule.setPharmacy(consultantWorkingPharmacy);
			consultationForSchedule.setPrice(consultantWorkingPharmacy.getPharmacistConsultationPrice());
			consultationForSchedule.setStatus(ConsultationStatus.SCHEDULED);
			consultationForSchedule.setTime(consultationDataRange);
			consultationRepository.save(consultationForSchedule);
		}else{
			consultationForSchedule = consultationRepository.getOne(consultation.getPredefinedConsultationID());
			consultationForSchedule.setConsultant(consultant);
			consultationForSchedule.setPatient(patientRepository.getOne(consultation.getPatientId()));
			consultationForSchedule.setPharmacy(consultantWorkingPharmacy);
			consultationForSchedule.setPrice(consultantWorkingPharmacy.getPharmacistConsultationPrice());
			consultationForSchedule.setStatus(ConsultationStatus.SCHEDULED);
			consultationForSchedule.setTime(consultationDataRange);
			consultationRepository.save(consultationForSchedule);
		}

		return consultationForSchedule;
	}

	@Override
	@Transactional
	public Consultation addPredefined(Long pharmacyId, AddPredefinedConsultationDTO consultationInfo) {
		Consultant consultant = consultantRepository.findLockedById(consultationInfo.getConsultantId()).orElse(null);
		if (consultant == null)
			throw new PSNotFoundException("The requested consultant does not exist.");

		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);
		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		Consultation consultation = initializeConsultation(consultationInfo, pharmacy, consultant);

		checkConsultantAvailability(pharmacyId, consultant, consultation.getTime());

		validateTimeIntervalForConsultation(consultation.getTime());

		return consultationRepository.save(consultation);
	}

	private Consultation initializeConsultation(AddPredefinedConsultationDTO consultationInfo, Pharmacy pharmacy,
			Consultant consultant) {
		Consultation consultation = new Consultation();
		consultation.setConsultant(consultant);
		consultation.setPharmacy(pharmacy);
		consultation.setStatus(ConsultationStatus.PREDEFINED);
		consultation.setTime(new DateRange(consultationInfo.getStart(), consultationInfo.getEnd()));
		if (consultationInfo.getPrice() == null) {
			if (consultant.getType().equals(ConsultantType.DERMATOLOGIST))
				consultation.setPrice(pharmacy.getDermatologistConsultationPrice());
			else
				consultation.setPrice(pharmacy.getPharmacistConsultationPrice());
		} else {
			consultation.setPrice(consultationInfo.getPrice());
		}

		return consultation;
	}

	private void checkConsultantAvailability(Long pharmacyId, Consultant consultant, DateRange interval) {
		if (!workingTimesService.consultationFitsIntoWorkingTime(consultant.getId(), pharmacyId, interval))
			throw new PSConflictException("Consultation doesn't fit into the consultants working time.");

		if (hasOverlappingConsultations(consultant, interval, ConsultationStatus.PREDEFINED))
			throw new PSConflictException("Consultant already has a predefined consultation in the time period.");

		if (hasOverlappingConsultations(consultant, interval, ConsultationStatus.SCHEDULED))
			throw new PSConflictException("Consultant already has a scheduled consultation in the time period.");

		if (hasOverlappingVacations(consultant, interval))
			throw new PSConflictException("Consultant already has a scheduled vacation in the time period.");
	}

	private boolean hasOverlappingConsultations(Consultant consultant, DateRange interval, ConsultationStatus status) {
		for (Consultation consultation : consultationRepository.findAllByConsultantAndStatus(consultant, status)) {
			if (DateConversionsAndComparisons.overlaps(interval, consultation.getTime()))
				return true;
		}

		return false;
	}

	private boolean hasOverlappingVacations(Consultant consultant, DateRange interval) {
		return CollectionUtil.contains(consultant.getVacations(),
				vacation -> DateConversionsAndComparisons.overlapsWithoutTime(vacation.getDateRange(), interval));
	}

	private void validateTimeIntervalForConsultation(DateRange interval) {
		if (interval.getStart().compareTo(interval.getEnd()) >= 0)
			throw new PSBadRequestException("Consultation start must be after the consultation end.");

		Date limit = new Date(new Date().getTime() + 24 * 60 * 60 * 1000L);

		if (interval.getStart().compareTo(limit) <= 0)
			throw new PSBadRequestException("Consultations must be scheduled at least 24 hours in advance.");
	}

	@Override
	public List<Consultant> findAllPatientConsultants(Patient patient) {
		List<Consultant> allPatientConsultants = new ArrayList<>();
		for (Consultation consultation : consultationRepository.findAllByPatient(patient)) {
			boolean alreadyExistConsultant = false;
			for (Consultant consultant : allPatientConsultants) {
				if (consultant.getId().equals(consultation.getConsultant().getId())) {
					alreadyExistConsultant = true;
					break;
				}
			}
			if (!alreadyExistConsultant)
				allPatientConsultants.add(consultation.getConsultant());
		}
		return allPatientConsultants;
	}

	@Override
	/*
	 * Find all pharmacies where patient had a consultation
	 */
	public List<Pharmacy> findAllPatientPharmacies(Patient patient) {
		List<Pharmacy> allPatientPharmacies = new ArrayList<>();
		for (Consultation consultation : consultationRepository.findAllByPatient(patient)) {
			boolean alreadyExistPharmacy = false;
			for (Pharmacy pharmacy : allPatientPharmacies) {
				if (pharmacy.getId().equals(consultation.getPharmacy().getId())) {
					alreadyExistPharmacy = true;
					break;
				}
			}
			if (!alreadyExistPharmacy)
				allPatientPharmacies.add(consultation.getPharmacy());
		}
		return allPatientPharmacies;
	}

	@Override
	public Set<Consultation> findAllConsultationByPatientAndStatus(PatientEmailDTO patientEmailDTO, ConsultationStatus status, ConsultantType consultantType) {
		Long patientId = userRepository.findByEmail(patientEmailDTO.getEmail()).getPerson().getId();
		Patient patient = patientRepository.findById(patientId).orElse(null);
		if(patient == null) throw new PSNotFoundException("Not found that patient");
		Set<Consultation> consultations = new HashSet<>();
		for(Consultation consultation : consultationRepository.findAllByPatientAndStatus(patient,status)){
			if(consultation.getConsultant().getType().equals(consultantType)){
				consultations.add(consultation);
			}
		}
		return consultations;
	}

	@Override
	public void cancelConsultation(PatientCancelConsultationDTO patientCancelConsultationDTO) {
		Consultation consultation = consultationRepository.findById(patientCancelConsultationDTO.getConsultationId()).orElse(null);
		if(consultation == null) throw new PSNotFoundException("Not found that consultation");
		consultation.setStatus(ConsultationStatus.CANCELLED);
		consultationRepository.save(consultation);
	}

	@Override
	public Boolean isConsultantFreeForConsultation(Long consultantId, Long pharmacyID, Date examinationStartDate, Date examinationEndDate) {
		Consultant consultant = consultantRepository.getOne(consultantId);

		for (Consultation c : consultant.getConsultations()) {
			// ESD izmedju CSD i CED
			// EED izmedju CSD i CED
			if (DateConversionsAndComparisons.compareDates(c.getTime().getStart(), examinationStartDate) <= 0
					&& DateConversionsAndComparisons.compareDates(c.getTime().getEnd(), examinationStartDate) >= 0
					&& DateConversionsAndComparisons.compareDates(c.getTime().getEnd(), examinationEndDate) >= 0
					&& DateConversionsAndComparisons.compareDates(c.getTime().getStart(), examinationEndDate) <= 0) {
				return false;
			}
		}

		return true;
	}

}
