package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.ratings.ConsultantRatingCreateDTO;
import com.pharmacy.cpis.userservice.dto.ratings.ConsultantRatingUpdateDTO;
import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.ratings.Rating;
import com.pharmacy.cpis.userservice.model.users.Patient;

import java.util.List;

public interface IRatingService {

    List<ConsultantRating> getAllConsultantRatingsByPatient(String patientEmail);
    ConsultantRating createConsultantRating(ConsultantRatingCreateDTO consultantRatingCreateDTO);

    ConsultantRating updateConsultantRating(ConsultantRatingUpdateDTO consultantRatingUpdateDTO);
}
