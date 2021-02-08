package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.repository.IReservationRepository;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Service
public class ReservationService implements IReservationService {
    @Autowired
    private IReservationRepository reservationRepository;

    @Override
    public Boolean isReservationValid(Long reservationID) {
        Reservation reservation = reservationRepository.getOne(reservationID);

        Boolean isValid = false;

        Instant now = Instant.now(); //current date
        Instant before24h = now.plus(Duration.ofDays(1));
        Date dateBefore24h = Date.from(before24h);
        
        if(reservationRepository.existsById(reservationID) && reservation.getDeadline().compareTo(dateBefore24h) > 0){
            isValid = true;
            return isValid;
        }

        return isValid;
    }
}
