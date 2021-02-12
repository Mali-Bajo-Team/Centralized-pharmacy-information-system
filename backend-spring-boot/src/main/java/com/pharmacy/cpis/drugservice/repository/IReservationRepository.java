package com.pharmacy.cpis.drugservice.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.pharmacy.cpis.userservice.model.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import org.springframework.data.jpa.repository.Query;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    Collection<Reservation> findAllByPharmacyIdAndDrugCode(Long pharmacyId, String drugCode);
    List<Reservation> findAllByPatient(Patient patient);

    @Query("select r from Reservation r where r.isPickedUp = false and "
            + "date_part('year',r.deadline) = :year and "+
            "date_part('month',r.deadline) = :month and "+
            "date_part('day',r.deadline) = :day")
    Set<Reservation> getUnclaimedReservationsForDate(int day, int month, int year);   
    
}
