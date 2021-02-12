package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.userservice.dto.ratings.*;
import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.ratings.DrugRating;
import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;

import java.util.List;

public interface IRatingService {

    List<ConsultantRating> getAllConsultantRatingsByPatient(String patientEmail);

    ConsultantRating createConsultantRating(ConsultantRatingCreateDTO consultantRatingCreateDTO);

    ConsultantRating updateConsultantRating(ConsultantRatingUpdateDTO consultantRatingUpdateDTO);

    List<PharmacyRating> getAllPharmacyRatingsByPatient(String patientEmail);

    PharmacyRating createPharmacyRating(PharmacyRatingCreateDTO pharmacyRatingCreateDTO);

    PharmacyRating updatePharmacyRating(PharmacyRatingUpdateDTO pharmacyRatingUpdateDTO);

    List<DrugRating> getAllDrugRatingsByPatient(String patientEmail);

    DrugRating createDrugRating(DrugRatingCreateDTO drugRatingCreateDTO);

    DrugRating updateDrugRating(DrugRatingUpdateDTO drugRatingUpdateDTO);
}
