package com.pharmacy.cpis.drugservice.service;


import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;

public interface IReservationService {
    Reservation saveReservation(DrugReservationDTO reservationDTO);
    ReservationDTO isReservationValid(ReservationDTO reservationDTO);
    ReservationDTO dispensingMedicine(ReservationDTO reservationDTO);


}
