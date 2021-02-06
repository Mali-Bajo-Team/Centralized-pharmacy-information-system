package com.pharmacy.cpis.userservice.controller;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.dto.PatientProfileDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import com.pharmacy.cpis.userservice.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "api/patient")
public class PatientController {
    @Autowired
    private IPatientService patientService;

    @Autowired
    private ILoyaltyProgramService loyaltyProgramService;

    @GetMapping("/profile")
    public  ResponseEntity<PatientProfileDTO> getPatient(@RequestBody PatientEmailDTO patientEmail) {
        Patient patient = patientService.findByEmail(patientEmail.getEmail());
        PatientProfileDTO patientProfileDTO=new PatientProfileDTO(patient,patientEmail,loyaltyProgramService);

        return new ResponseEntity<>(patientProfileDTO, HttpStatus.OK);

    }
}
