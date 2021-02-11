package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.ratings.Rating;
import com.pharmacy.cpis.userservice.model.users.Patient;

import java.util.List;

public interface IRatingService {

    List<ConsultantRating> getAllConsultantRatingsByPatient(String patientEmail);
}
