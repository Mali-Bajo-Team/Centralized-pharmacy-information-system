package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.repository.IReservationRepository;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService implements IReservationService {
    @Autowired
    private IReservationRepository reservationRepository;

    @Override
    public Boolean isReservationValid(Long reservationID) {
        Boolean isValid = false;
        if(reservationRepository.existsById(reservationID)){
            isValid = true;
            return isValid;
        }
        return isValid;
    }
}
