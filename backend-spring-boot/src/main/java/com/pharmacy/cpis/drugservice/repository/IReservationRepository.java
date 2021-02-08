package com.pharmacy.cpis.drugservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

	Iterable<Reservation> findAllByPharmacyIdAndDrugCode(Long pharmacyId, String drugCode);

}
