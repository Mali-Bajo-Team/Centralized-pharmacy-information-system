package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;

public interface IReservationService {
    ReservationDTO isReservationValid(ReservationDTO reservationDTO);
}
