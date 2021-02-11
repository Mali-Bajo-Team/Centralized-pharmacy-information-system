package com.pharmacy.cpis.userservice.controller;


import com.pharmacy.cpis.userservice.dto.LoyaltyProgramDTO;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.dto.ratings.ConsultantRatingReadDTO;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.users.Consultant;
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
    public ResponseEntity<List<ConsultantRatingReadDTO>> getLoyaltyPrograms(@RequestBody PatientEmailDTO patientEmailDTO) {
        List<ConsultantRatingReadDTO> consultantRatingReadDTOS = new ArrayList<>();
        for (ConsultantRating consultantRating : ratingService.getAllConsultantRatingsByPatient(patientEmailDTO.getEmail())) {
            consultantRatingReadDTOS.add(new ConsultantRatingReadDTO(consultantRating));
        }
        return new ResponseEntity<>(consultantRatingReadDTOS, HttpStatus.OK);
    }

}
