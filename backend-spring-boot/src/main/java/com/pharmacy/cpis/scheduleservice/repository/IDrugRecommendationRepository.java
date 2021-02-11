package com.pharmacy.cpis.scheduleservice.repository;

import com.pharmacy.cpis.scheduleservice.model.prescriptions.DrugRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrugRecommendationRepository extends JpaRepository<DrugRecommendation, Long> {
}
