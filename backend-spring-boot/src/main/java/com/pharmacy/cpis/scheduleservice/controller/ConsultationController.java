package com.pharmacy.cpis.scheduleservice.controller;

import com.pharmacy.cpis.scheduleservice.dto.ConsultationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/consultations")
public class ConsultationController {

    @Autowired
    private IConsultationService consultationService;

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

    @GetMapping("activate/{id}")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<ConsultationDTO>> getAllConsultationsForConsultant(@PathVariable Long id) {

        List<Consultation> consultations = consultationService.findAll();

        List<ConsultationDTO> ConsultationDTOs = new ArrayList<>();
        for (Consultation c : consultations) {
            if(c.getId().equals(id)) {
                ConsultationDTOs.add(new ConsultationDTO(c));
            }
        }

        return new ResponseEntity<>(ConsultationDTOs, HttpStatus.OK);
    }
}
