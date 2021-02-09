package com.pharmacy.cpis.drugservice.service;


import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.model.users.Patient;

import java.util.List;

public interface IReservationService {
    Reservation saveReservation(DrugReservationDTO reservationDTO);

    ReservationDTO isReservationValid(ReservationDTO reservationDTO);

    ReservationDTO dispensingMedicine(ReservationDTO reservationDTO);

    // All pharmacies where that patient make reservation for drug
    List<Pharmacy> findAllPatientPharmacies(Patient patient);
}
