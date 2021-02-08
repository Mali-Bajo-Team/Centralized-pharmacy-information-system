package com.pharmacy.cpis.drugservice.controller;

import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reservation")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;


    @PostMapping(value = "/isvalid")
    public ResponseEntity<ReservationDTO> getDrugPharmaciesPrices(@RequestBody ReservationDTO reservationDTO){

        Boolean isReservationValid = reservationService.isReservationValid(reservationDTO);
        reservationDTO.setValid(isReservationValid);


        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

}
