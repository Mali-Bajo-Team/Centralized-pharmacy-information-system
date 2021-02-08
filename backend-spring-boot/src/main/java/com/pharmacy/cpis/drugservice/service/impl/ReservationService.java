package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IReservationRepository;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.userservice.service.IPatientService;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IDrugRepository drugRepository;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IPharmacyService pharmacyService;

    @Override
    public Reservation saveReservation(DrugReservationDTO reservationDTO) {
        Reservation reservation=new Reservation();
        reservation.setAmount(reservationDTO.getAmount());
        reservation.setDeadline(reservationDTO.getDeadline());
        reservation.setPatient(patientService.findByEmail(reservationDTO.getPatientEmail()));
        reservation.setDateOfCreation(reservationDTO.getDateOfCreation());
        reservation.setPharmacy(pharmacyService.getById(reservationDTO.getPharmacyID()));
        reservation.setDrug(drugRepository.findByCode(reservationDTO.getDrugCode()));
        reservation.setIsPickedUp(false);


        return reservationRepository.save(reservation);
    }

}
