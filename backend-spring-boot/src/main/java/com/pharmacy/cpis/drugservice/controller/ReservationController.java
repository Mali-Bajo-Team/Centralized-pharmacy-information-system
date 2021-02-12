package com.pharmacy.cpis.drugservice.controller;


import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.dto.PatientDrugForRatingReadDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import com.pharmacy.cpis.util.aspects.PatientAccountPenalties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reservation")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IAvailableDrugService availableDrugService;

    @PreAuthorize("hasRole('PATIENT')")
    @PatientAccountPenalties
    @PostMapping(value = "/drug")
    public ResponseEntity<DrugReservationDTO> makeReservationOfDrug(@RequestBody DrugReservationDTO drugReservationDTO){
        Reservation reservation = reservationService.makeReservation(drugReservationDTO);
        return new ResponseEntity<>(new DrugReservationDTO(reservation) , HttpStatus.OK);
    }

    @PostMapping("/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<DrugReservationDTO>> getAllPatientReservations(@RequestBody PatientEmailDTO patientEmailDTO) {
        List<Reservation> reservations= reservationService.findAllPatientReservations(patientEmailDTO);
        List<DrugReservationDTO> drugReservationDTOs=new ArrayList<>();
        for(Reservation reservation: reservations){
            drugReservationDTOs.add(new DrugReservationDTO(reservation));
        }
        return new ResponseEntity<>(drugReservationDTOs,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping(value = "/drug/ratings")
    public ResponseEntity<List<PatientDrugForRatingReadDTO>> getPatientDrugsAvailableForRatings(@RequestBody PatientEmailDTO patientEmailDTO){
        List<PatientDrugForRatingReadDTO> patientDrugForRatingReadDTOS = new ArrayList<>();
        for(Drug drug : reservationService.findAllPatientDrugsAvailableForRating(patientEmailDTO.getEmail())){
            patientDrugForRatingReadDTOS.add(new PatientDrugForRatingReadDTO(drug));
        }
        return new ResponseEntity<>(patientDrugForRatingReadDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PHARMACIST')")
    @EmployeeAccountActive
    @PostMapping(value = "/isvalid")
    public ResponseEntity<ReservationDTO> getDrugPharmaciesPrices(@RequestBody ReservationDTO reservationDTO){
        ReservationDTO reservationDTOforReturn = reservationService.isReservationValid(reservationDTO);
        return new ResponseEntity<>(reservationDTOforReturn, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PHARMACIST')")
    @EmployeeAccountActive
    @PostMapping(value = "/dispense")
    public ResponseEntity<ReservationDTO> dispensingMedicine(@RequestBody ReservationDTO reservationDTO){
        reservationService.dispensingMedicine(reservationDTO);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }
    @DeleteMapping(value = "/drug", consumes = "application/json")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Void> cancelDrugReservation(@RequestBody DrugReservationDTO drugReservationDTO){
        reservationService.removeReservation(drugReservationDTO.getReservationId());
        availableDrugService.updateAmount(drugReservationDTO.getPharmacyID(),drugReservationDTO.getDrugCode(),-drugReservationDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
