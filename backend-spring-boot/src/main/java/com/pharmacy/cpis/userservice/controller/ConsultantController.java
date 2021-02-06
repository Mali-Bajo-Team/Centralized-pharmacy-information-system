package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.userservice.dto.ExaminitedPatientDTO;
import com.pharmacy.cpis.userservice.dto.PatientDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.IPatientService;
import com.pharmacy.cpis.userservice.service.IUserService;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/consultant")
public class ConsultantController {
    @Autowired
    private IConsultantService consultantService;

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/examinitedpatients")
    @PreAuthorize("hasRole('PHARMACIST')")
    @EmployeeAccountActive
    public ResponseEntity<Set<ExaminitedPatientDTO>> getExaminitedPatients(@RequestBody ExaminitedPatientDTO examinitedPatientDTO) {

        UserAccount loggedPharmacist = userService.findByEmail(examinitedPatientDTO.getConsultantEmail());
        Set<ExaminitedPatientDTO> examinitedPatients = consultantService.getExaminitedPatients(loggedPharmacist.getId());

        return new ResponseEntity<>(examinitedPatients, HttpStatus.OK);
    }
}
