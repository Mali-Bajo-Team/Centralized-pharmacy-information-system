package com.pharmacy.cpis.drugservice.service;


import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;
import org.joda.time.LocalDate;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface IReservationService {
    Reservation makeReservation(DrugReservationDTO reservationDTO);

    void makeReservationForEPrescription(EPrescription ePrescription,  Long pharmacyIdWhereIsMadeReservation);

    ReservationDTO isReservationValid(ReservationDTO reservationDTO);

    ReservationDTO dispensingMedicine(ReservationDTO reservationDTO);

    // All pharmacies where that patient make reservation for drug
    List<Pharmacy> findAllPatientPharmacies(Patient patient);

    List<Reservation> findAllPatientReservations(PatientEmailDTO patientEmailDTO);

    void removeReservation(Long reservationId);

    // All reserved drugs (there is also ones which are made with ePrescription)
    List<Drug> findAllPatientDrugsAvailableForRating(String patientEmail);

    Set<Reservation> getUnclaimedReservationsForDate(LocalDate date);
}
