package com.pharmacy.cpis.scheduleservice.controller;

import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.service.IWorkingTimesService;
import com.pharmacy.cpis.userservice.dto.ConsultantDTO;
import com.pharmacy.cpis.scheduleservice.dto.ConsultationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/consultations")
public class ConsultationController {

	@Autowired
	private IConsultationService consultationService;
	@Autowired
	private IWorkingTimesService workingTimesService;
	@Autowired
	private IUserService userService;

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
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<List<ConsultationDTO>> getAllConsultationsForConsultant(
			@RequestBody ConsultantDTO consultantDTO) {

		List<Consultation> consultations = consultationService.findAll();
		List<ConsultationDTO> ConsultationDTOs = new ArrayList<>();
		UserAccount loggedPharmacist = (UserAccount) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		for (Consultation c : consultations) {
			if (loggedPharmacist.getId().equals(c.getConsultant().getId())) {
				ConsultationDTOs.add(new ConsultationDTO(c));
			}
		}

		return new ResponseEntity<>(ConsultationDTOs, HttpStatus.OK);
	}

	@PostMapping("/loggedconsultant")
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<ConsultantDTO> getLoggedConsultant(@RequestBody ConsultantDTO consultantDTO) {

		UserAccount loggedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

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
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<ScheduleExaminationDTO> scheduleConsultation(@RequestBody ScheduleExaminationDTO scheduleExaminationDTO) {

		Date javaDate = null;
		try {
			javaDate=new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(scheduleExaminationDTO.getStartDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		UserAccount loggedPharmacist = userService.findByEmail(scheduleExaminationDTO.getConsultantEmail());
		Boolean isConsultantWorkingTime = workingTimesService.isConsultantWorkingTime(loggedPharmacist.getId(), javaDate);

		if(isConsultantWorkingTime){
			System.out.println("DOBRO VREMEWEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
			return new ResponseEntity<ScheduleExaminationDTO>(scheduleExaminationDTO, HttpStatus.OK);
		}
		System.out.println("LoSE VREMEWEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
		return new ResponseEntity<ScheduleExaminationDTO>(scheduleExaminationDTO, HttpStatus.FORBIDDEN);
	}
}
