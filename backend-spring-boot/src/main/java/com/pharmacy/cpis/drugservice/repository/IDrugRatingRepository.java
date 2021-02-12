package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.userservice.model.ratings.DrugRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDrugRatingRepository extends JpaRepository<DrugRating, Long> {
    List<DrugRating> findByDrugCode(String drugCode);
    List<DrugRating> findAllByPatientId(Long patientId);
}
