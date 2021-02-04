package com.pharmacy.cpis.scheduleservice.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/consultations")
public class ConsultationController {

    @Autowired
    private IConsultationService consultationService;
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
    public ResponseEntity<List<ConsultationDTO>> getAllConsultationsForConsultant(@RequestBody ConsultantDTO consultantDTO) {

        List<Consultation> consultations = consultationService.findAll();
        List<ConsultationDTO> ConsultationDTOs = new ArrayList<>();
        UserAccount loggedPharmacist = userService.findByEmail(consultantDTO.getEmail());

        for (Consultation c : consultations) {
            if(loggedPharmacist.getId() == c.getConsultant().getId()) {
                ConsultationDTOs.add(new ConsultationDTO(c));
            }
        }

        return new ResponseEntity<>(ConsultationDTOs, HttpStatus.OK);
    }

    @PostMapping("/loggedconsultant")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<ConsultantDTO> getLoggedConsultant(@RequestBody ConsultantDTO consultantDTO) {

        UserAccount loggedPharmacist = userService.findByEmail(consultantDTO.getEmail());

        consultantDTO.setName(loggedPharmacist.getPerson().getName());
        consultantDTO.setLastName(loggedPharmacist.getPerson().getSurname());
        consultantDTO.setPhoneNumber(loggedPharmacist.getPerson().getPhoneNumber());
        consultantDTO.setLocation(loggedPharmacist.getPerson().getAddress());

        return new ResponseEntity<ConsultantDTO>(consultantDTO, HttpStatus.OK);
    }
}
