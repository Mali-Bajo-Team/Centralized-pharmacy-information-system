package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IPatientRepository extends JpaRepository<Patient, Long> {
    @Modifying
    @Query("update Patient p set p.penalties = 0") void resetPenalties();

}
