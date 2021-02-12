package com.pharmacy.cpis.userservice.controller;


import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.dto.ratings.*;
import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;
import com.pharmacy.cpis.userservice.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/ratings")
public class RatingController {

    @Autowired
    private IRatingService ratingService;


    @PostMapping(value = "/consultant")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<ConsultantRatingReadDTO>> getPatientConsultantRatings(@RequestBody PatientEmailDTO patientEmailDTO) {
        List<ConsultantRatingReadDTO> consultantRatingReadDTOS = new ArrayList<>();
        for (ConsultantRating consultantRating : ratingService.getAllConsultantRatingsByPatient(patientEmailDTO.getEmail())) {
            consultantRatingReadDTOS.add(new ConsultantRatingReadDTO(consultantRating));
        }
        return new ResponseEntity<>(consultantRatingReadDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/consultant/create")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ConsultantRatingReadDTO> savePatientConsultantRatings(@RequestBody ConsultantRatingCreateDTO consultantRatingCreateDTO) {
        ConsultantRating consultantRating = ratingService.createConsultantRating(consultantRatingCreateDTO);
        return new ResponseEntity<>(new ConsultantRatingReadDTO(consultantRating),HttpStatus.OK);
    }

    @PostMapping(value = "/consultant/update")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ConsultantRatingReadDTO> updatePatientConsultantRatings(@RequestBody ConsultantRatingUpdateDTO consultantRatingUpdateDTO) {
        ConsultantRating consultantRating = ratingService.updateConsultantRating(consultantRatingUpdateDTO);
        return new ResponseEntity<>(new ConsultantRatingReadDTO(consultantRating),HttpStatus.OK);
    }

    @PostMapping(value = "/pharmacy")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<PharmacyRatingReadDTO>> getPatientPharmacyRatings(@RequestBody PatientEmailDTO patientEmailDTO) {
        List<PharmacyRatingReadDTO> pharmacyRatingReadDTOS = new ArrayList<>();
        for(PharmacyRating pharmacyRating : ratingService.getAllPharmacyRatingsByPatient(patientEmailDTO.getEmail())){
            pharmacyRatingReadDTOS.add(new PharmacyRatingReadDTO(pharmacyRating));
        }
        return new ResponseEntity<>(pharmacyRatingReadDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/pharmacy/create")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PharmacyRatingReadDTO> savePatientPharmacyRatings(@RequestBody PharmacyRatingCreateDTO pharmacyRatingCreateDTO) {
        PharmacyRating pharmacyRating = ratingService.createPharmacyRating(pharmacyRatingCreateDTO);
        return new ResponseEntity<>(new PharmacyRatingReadDTO(pharmacyRating),HttpStatus.OK);
    }

    @PostMapping(value = "/pharmacy/update")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PharmacyRatingReadDTO> updatePatientPharmacyRatings(@RequestBody PharmacyRatingUpdateDTO consultantRatingUpdateDTO) {
        PharmacyRating consultantRating = ratingService.updatePharmacyRating(consultantRatingUpdateDTO);
        return new ResponseEntity<>(new PharmacyRatingReadDTO(consultantRating),HttpStatus.OK);
    }


}
