package com.pharmacy.cpis.scheduleservice.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.scheduleservice.dto.AddPredefinedConsultationDTO;
import com.pharmacy.cpis.scheduleservice.dto.ConsultationDTO;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import com.pharmacy.cpis.userservice.dto.ConsultantDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.service.EmailService;
import com.pharmacy.cpis.userservice.service.IUserService;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;

@RestController
@RequestMapping(value = "api/consultations")
public class ConsultationController {
	@Autowired
	private IPatientRepository patientRepository;
	@Autowired
	private IConsultationService consultationService;
	@Autowired
	private IWorkingTimesService workingTimesService;
	@Autowired
	private IUserService userService;
	@Autowired
	private EmailService emailService;

	@GetMapping
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<List<ConsultationDTO>> getAllConsultations() {

		List<Consultation> consultations = consultationService.findAll();

		List<ConsultationDTO> ConsultationDTOs = new ArrayList<>();
		for (Consultation c : consultations) {
			ConsultationDTOs.add(new ConsultationDTO(c));
		}

		return new ResponseEntity<>(ConsultationDTOs, HttpStatus.OK);
	}

	@PostMapping("/consultantexaminations")
	@PreAuthorize("hasRole('PHARMACIST')|| hasRole('DERMATOLOGIST')")
	public ResponseEntity<List<ConsultationDTO>> getAllConsultationsForConsultant(
			@RequestBody ConsultantDTO consultantDTO) {

		List<Consultation> consultations = consultationService.findAll();
		List<ConsultationDTO> ConsultationDTOs = new ArrayList<>();
		UserAccount loggedPharmacist = (UserAccount) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		for (Consultation c : consultations) {
			if (loggedPharmacist.getId().equals(c.getConsultant().getId()) && c.getStatus().equals(ConsultationStatus.SCHEDULED)) {
				ConsultationDTOs.add(new ConsultationDTO(c));
			}
		}

		return new ResponseEntity<>(ConsultationDTOs, HttpStatus.OK);
	}

	@PostMapping("/loggedconsultant")
	@PreAuthorize("hasRole('PHARMACIST')|| hasRole('DERMATOLOGIST')")
	public ResponseEntity<ConsultantDTO> getLoggedConsultant(@RequestBody ConsultantDTO consultantDTO) {

		UserAccount loggedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserAccount loggedPharmacist = userService.findByEmail(loggedUser.getEmail());

		consultantDTO.setName(loggedPharmacist.getPerson().getName());
		consultantDTO.setLastName(loggedPharmacist.getPerson().getSurname());
		consultantDTO.setPhoneNumber(loggedPharmacist.getPerson().getPhoneNumber());
		consultantDTO.setLocation(loggedPharmacist.getPerson().getAddress());
		consultantDTO.setCity(loggedPharmacist.getPerson().getCity());
		consultantDTO.setCountry(loggedPharmacist.getPerson().getCountry());

		return new ResponseEntity<ConsultantDTO>(consultantDTO, HttpStatus.OK);
	}

	@PostMapping("/scheduleconsultation")
	@PreAuthorize("hasRole('PHARMACIST') || hasRole('DERMATOLOGIST')")
	public ResponseEntity<ScheduleExaminationDTO> scheduleConsultation(
			@RequestBody ScheduleExaminationDTO scheduleExaminationDTO) throws InterruptedException {

		Date examinationStartDate = DateConversionsAndComparisons.getUtilDate(scheduleExaminationDTO.getStartDate());
		Date examinationEndDate = DateConversionsAndComparisons.getUtilDate(scheduleExaminationDTO.getEndDate());

		UserAccount loggedPharmacist = userService.findByEmail(scheduleExaminationDTO.getConsultantEmail());
		scheduleExaminationDTO.setConsultantId(loggedPharmacist.getId());

		Boolean isConsultationTimeFitsIntoConsultantWorkingTime = workingTimesService
				.isConsultationTimeFitsIntoConsultantWorkingTime(loggedPharmacist.getId(), examinationStartDate,
						examinationEndDate);

		Boolean isPhatientFreeForConsultation = consultationService.isPhatientFreeForConsultation(
				scheduleExaminationDTO.getPatientId(), examinationStartDate, examinationEndDate);

		if (isConsultationTimeFitsIntoConsultantWorkingTime && isPhatientFreeForConsultation
				&& !examinationStartDate.before(new Date())) {
			consultationService.scheduleConsultation(scheduleExaminationDTO);

			Patient patient = patientRepository.getOne(scheduleExaminationDTO.getPatientId());
			emailService.sendConfirmConsultationEmailAsync(patient.getAccount().getUsername(),
					patient.getAccount().getEmail(), scheduleExaminationDTO);

			return new ResponseEntity<ScheduleExaminationDTO>(scheduleExaminationDTO, HttpStatus.OK);
		}

		return new ResponseEntity<ScheduleExaminationDTO>(scheduleExaminationDTO, HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/predefined", consumes = "application/json")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<ConsultationDTO> addPredefined(
			@RequestBody @Valid AddPredefinedConsultationDTO consultationInfo, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Consultation consultation = consultationService.addPredefined(user.getPharmacyId(), consultationInfo);

		return ResponseEntity.ok(new ConsultationDTO(consultation));
	}

	@GetMapping(value = "/predefined")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<Collection<ConsultationDTO>> getPredefined(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		if (user.getPharmacyId() == null)
			throw new PSForbiddenException("You are not authorized to administrate a pharmacy.");

		Collection<Consultation> consultations = consultationService.findByPharmacyAndStatus(user.getPharmacyId(),
				ConsultationStatus.PREDEFINED);
		Collection<ConsultationDTO> mapped = CollectionUtil.map(consultations,
				consultation -> new ConsultationDTO(consultation));

		return ResponseEntity.ok(mapped);
	}

	@GetMapping(value = "/predefined/pharmacy/{id}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Collection<ConsultationDTO>> getPredefinedByPharmacy(@PathVariable(required = true) Long id) {
		Collection<Consultation> consultations = consultationService.findByPharmacyAndStatus(id,
				ConsultationStatus.PREDEFINED);
		Collection<ConsultationDTO> mapped = CollectionUtil.map(consultations,
				consultation -> new ConsultationDTO(consultation));

		return ResponseEntity.ok(mapped);
	}

}
