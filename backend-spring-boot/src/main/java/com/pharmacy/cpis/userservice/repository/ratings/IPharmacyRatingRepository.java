package com.pharmacy.cpis.userservice.repository.ratings;

import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPharmacyRatingRepository extends JpaRepository<PharmacyRating, Long> {
    List<PharmacyRating> findAllByPatientId(Long patientId);
}
