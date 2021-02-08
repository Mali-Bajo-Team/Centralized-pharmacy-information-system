package com.pharmacy.cpis.drugservice.controller;

import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/reservation")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @PostMapping(value = "/drug")
    public ResponseEntity<DrugReservationDTO> makeReservationOfDrug(@RequestBody DrugReservationDTO drugReservationDTO){
        Reservation reservation = reservationService.saveReservation(drugReservationDTO);
        return new ResponseEntity<>(new DrugReservationDTO(reservation) , HttpStatus.OK);
    }
}
