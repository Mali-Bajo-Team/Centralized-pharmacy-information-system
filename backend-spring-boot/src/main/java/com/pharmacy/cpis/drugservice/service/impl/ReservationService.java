package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.repository.IReservationRepository;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService implements IReservationService {
    @Autowired
    private IReservationRepository reservationRepository;
    @Autowired
    private IConsultantService consultantService;
    @Autowired
    private IPharmacyService pharmacyService;
    @Override
    public Boolean isReservationValid(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.getOne(reservationDTO.getReservationID());

        Boolean isValid = false;

        Instant now = Instant.now(); //current date
        Instant before24h = now.plus(Duration.ofDays(1));
        Date dateBefore24h = Date.from(before24h);

        Consultant consultant = consultantService.findByEmail(reservationDTO.getConsultantEmail());
        List<Pharmacy> allPharmacies = pharmacyService.findAll();

        //find pharmacy where consultant work amd check is reservation valid in that pharmacy
        for (Pharmacy p : allPharmacies) {
            for (WorkingTimes c : reservation.getPharmacy().getConsultants()){
                if(c.getConsultant().getId().equals(consultant.getId())){
                    if(reservation.getPharmacy().getId().equals(reservationDTO.getReservationID())){
                        if(reservationRepository.existsById(reservationDTO.getReservationID()) && reservation.getDeadline().compareTo(dateBefore24h) > 0){
                            isValid = true;
                            return isValid;
                        }
                    }
                }
            }
        }

        return isValid;
    }
}
