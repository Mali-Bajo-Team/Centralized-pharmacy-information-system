package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.ratings.ConsultantRatingCreateDTO;
import com.pharmacy.cpis.userservice.dto.ratings.ConsultantRatingUpdateDTO;
import com.pharmacy.cpis.userservice.dto.ratings.PharmacyRatingCreateDTO;
import com.pharmacy.cpis.userservice.dto.ratings.PharmacyRatingUpdateDTO;
import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;

import java.util.List;

public interface IRatingService {

    List<ConsultantRating> getAllConsultantRatingsByPatient(String patientEmail);

    ConsultantRating createConsultantRating(ConsultantRatingCreateDTO consultantRatingCreateDTO);

    ConsultantRating updateConsultantRating(ConsultantRatingUpdateDTO consultantRatingUpdateDTO);

    List<PharmacyRating> getAllPharmacyRatingsByPatient(String patientEmail);

    PharmacyRating createPharmacyRating(PharmacyRatingCreateDTO pharmacyRatingCreateDTO);

    PharmacyRating updatePharmacyRating(PharmacyRatingUpdateDTO pharmacyRatingUpdateDTO);
}
