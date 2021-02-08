package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;

public interface IReservationService {
    Boolean isReservationValid(Long reservationID);
}
