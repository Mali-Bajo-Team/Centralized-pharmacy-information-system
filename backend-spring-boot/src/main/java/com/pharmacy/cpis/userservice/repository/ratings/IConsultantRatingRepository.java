package com.pharmacy.cpis.userservice.repository.ratings;

import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConsultantRatingRepository extends JpaRepository<ConsultantRating, Long> {
    List<ConsultantRating> findAllByPatientId(Long patientId);
}
