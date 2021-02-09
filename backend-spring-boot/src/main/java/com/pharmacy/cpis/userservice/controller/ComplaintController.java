package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.userservice.dto.ComplaintDTO;
import com.pharmacy.cpis.userservice.dto.ConsultantDTO;
import com.pharmacy.cpis.userservice.dto.CreateComplaintDTO;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.Person;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IComplaintService;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.IPatientService;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/complaints")
public class ComplaintController {

    @Autowired
    private IComplaintService complaintService;

    @Autowired
    private IConsultantService consultantService;

    @Autowired
    private IConsultationService consultationService;

    @Autowired
    private IPatientService patientService;

    @PostMapping("/consultants")
//    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<ConsultantDTO>> getAllPossibleConsultantsForComplaint(@RequestBody PatientEmailDTO patientEmail){
        Patient patient = patientService.findByEmail(patientEmail.getEmail());
        List<ConsultantDTO> consultants = new ArrayList<>();
        for(Consultant consultant : consultationService.findAllPatientConsultants(patient))
            consultants.add(new ConsultantDTO(consultant));
        return new ResponseEntity<>(consultants,HttpStatus.OK);
    }

    @PostMapping("/pharmacies")
//    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity< List<PharmacyDTO>> getAllPossiblePharmaciesForComplaint(@RequestBody PatientEmailDTO patientEmail){
        Patient patient = patientService.findByEmail(patientEmail.getEmail());
        List<PharmacyDTO> pharmacies = new ArrayList<>();
        for(Pharmacy pharmacy: complaintService.findAllPatientPharmacies(patient))
            pharmacies.add(new PharmacyDTO(pharmacy));
        return new ResponseEntity<>(pharmacies,HttpStatus.OK);
    }
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints(){
        List<ComplaintDTO> complaintDTOS = new ArrayList<>();
        for(Complaint complaint:  complaintService.findAllComplaints())
            complaintDTOS.add(new ComplaintDTO(complaint));
        return new ResponseEntity<>(complaintDTOS, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplaintDTO> updateComplaintResponse(@RequestBody ComplaintDTO complaintDTO){
        Complaint complaint = complaintService.updateComplaintResponse(complaintDTO);
        return new ResponseEntity<>(new ComplaintDTO(complaint),HttpStatus.OK);
    }

    @PostMapping()
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplaintDTO> createComplaint(@RequestBody CreateComplaintDTO complaintDTO){
        Complaint complaint = complaintService.createComplaint(complaintDTO);
        return new ResponseEntity<>(new ComplaintDTO(complaint),HttpStatus.OK);
    }
}
