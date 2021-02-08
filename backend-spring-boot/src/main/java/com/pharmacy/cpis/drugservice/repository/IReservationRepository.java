package com.pharmacy.cpis.drugservice.repository;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

}
