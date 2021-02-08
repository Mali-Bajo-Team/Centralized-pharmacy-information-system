package com.pharmacy.cpis.drugservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

Collection<Reservation> findAllByPharmacyIdAndDrugCode(Long pharmacyId, String drugCode);

}
