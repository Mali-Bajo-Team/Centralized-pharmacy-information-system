package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDTO;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDetailsDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.dto.PatientProfileDTO;
import com.pharmacy.cpis.userservice.dto.PenaltieDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import com.pharmacy.cpis.userservice.service.IPatientService;
import com.pharmacy.cpis.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "api/patient")
public class PatientController {
    @Autowired
    private IPatientService patientService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ILoyaltyProgramService loyaltyProgramService;

    @PostMapping("/profile")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientProfileDTO> getPatient(@RequestBody PatientEmailDTO patientEmail) {
        Patient patient = patientService.findByEmail(patientEmail.getEmail());
        return new ResponseEntity<>(new PatientProfileDTO(patient, patientEmail.getEmail(), loyaltyProgramService), HttpStatus.OK);
    }

    @PostMapping("/profileupdate")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientProfileDTO> updatePatient(@RequestBody PatientProfileDTO patientProfileDTO) {
        Patient patient = patientService.update(patientProfileDTO);
        return new ResponseEntity<>(new PatientProfileDTO(patient, patientProfileDTO.getEmail(), loyaltyProgramService),HttpStatus.OK);
    }

    @PostMapping("/addpenaltie")
    @PreAuthorize("hasRole('PHARMACIST') || hasRole('DERMATOLOGIST')")
    public ResponseEntity<PenaltieDTO> addPenaltie(@RequestBody PenaltieDTO penaltieDTO) {

        UserAccount userAccount = userRepository.getOne(penaltieDTO.getPhatientID());
        Patient patient = patientService.addPenaltie(userAccount.getEmail(), penaltieDTO.getConsultationID());

        return new ResponseEntity<>(penaltieDTO, HttpStatus.OK);
    }

    @PostMapping("/subscriptions")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<PharmacyDTO>> getPatientSubscriptions(@RequestBody PatientEmailDTO patientEmail) {
        List<PharmacyDTO> pharmacyDTOS = new ArrayList<>();
        for (Pharmacy pharmacy : patientService.findPatientSubscriptions(patientEmail.getEmail()))
            pharmacyDTOS.add(new PharmacyDTO(pharmacy));
        return new ResponseEntity<>(pharmacyDTOS, HttpStatus.OK);

    }

}
