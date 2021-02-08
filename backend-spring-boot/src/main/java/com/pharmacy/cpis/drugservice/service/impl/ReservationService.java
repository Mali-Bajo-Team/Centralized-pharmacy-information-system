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
    public ReservationDTO isReservationValid(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.getOne(reservationDTO.getReservationID());

        Boolean isValid = false;

        Instant now = Instant.now(); //current date
        Instant before24h = now.plus(Duration.ofDays(1));
        Date dateBefore24h = Date.from(before24h);

        Consultant consultant = consultantService.findByEmail(reservationDTO.getConsultantEmail());

        Pharmacy pharmacy = findPharmacyWhereConsultantWork(consultant.getId());
        if(pharmacy.getId().equals(reservation.getPharmacy().getId())){
            if(reservationRepository.existsById(reservationDTO.getReservationID()) && reservation.getDeadline().compareTo(dateBefore24h) > 0){
                isValid = true;
                reservationDTO.setValid(isValid);
                reservationDTO.setAmount(reservation.getAmount());
                reservationDTO.setDateOfCreation(reservation.getDateOfCreation());
                reservationDTO.setDeadLine(reservation.getDeadline());
                reservationDTO.setPhatientName(reservation.getPatient().getName());
                reservationDTO.setPharmacyName(reservation.getPharmacy().getName());
                return reservationDTO;
            }
        }

        reservationDTO.setValid(isValid);

        return reservationDTO;
    }

    @Override
    public ReservationDTO dispensingMedicine(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.getOne(reservationDTO.getReservationID());

        reservation.setIsPickedUp(true);

        reservationRepository.save(reservation);
        //Kad izdam lek reba da kreiram Drug Purcharse, smanjim amount i izracunam cenu


        return reservationDTO;
    }


    Pharmacy findPharmacyWhereConsultantWork(Long consultantID){
        List<Pharmacy> allPharmacies = pharmacyService.findAll();

        for (Pharmacy p : allPharmacies) {
            for(WorkingTimes wt : p.getConsultants()){
                if(wt.getConsultant().getId().equals(consultantID)){
                    return p;
                }
            }
        }
        return new Pharmacy();
    }
}
