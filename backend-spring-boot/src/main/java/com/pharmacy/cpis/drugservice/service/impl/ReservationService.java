package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IReservationRepository;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.userservice.service.EmailService;
import com.pharmacy.cpis.userservice.service.IPatientService;
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

    @Autowired
    private IAvailableDrugService availableDrugService;

    @Autowired
    private EmailService emailService;

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

        availableDrugService.updateAmount(reservationDTO.getPharmacyID(),reservationDTO.getDrugCode(),reservationDTO.getAmount());

        try {
            System.out.println("Sending mail in process ..");
            emailService.sendConfirmReservationOfDrugEmailAsync(reservationDTO.getPatientEmail(),
                    reservationDTO, reservation);

        } catch (Exception e) {
            System.out.println("Error during sending email: " + e.getMessage());
        }

        return reservationRepository.save(reservation);
    }

}
