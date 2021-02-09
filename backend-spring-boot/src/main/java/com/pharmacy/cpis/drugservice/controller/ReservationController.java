package com.pharmacy.cpis.drugservice.controller;


import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/reservation")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping(value = "/drug")
    public ResponseEntity<DrugReservationDTO> makeReservationOfDrug(@RequestBody DrugReservationDTO drugReservationDTO){
        Reservation reservation = reservationService.saveReservation(drugReservationDTO);
        return new ResponseEntity<>(new DrugReservationDTO(reservation) , HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DERMATOLOGIST')")
    @EmployeeAccountActive
    @PostMapping(value = "/isvalid")
    public ResponseEntity<ReservationDTO> getDrugPharmaciesPrices(@RequestBody ReservationDTO reservationDTO){

        ReservationDTO reservationDTOforReturn = reservationService.isReservationValid(reservationDTO);

        return new ResponseEntity<>(reservationDTOforReturn, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DERMATOLOGIST')")
    @EmployeeAccountActive
    @PostMapping(value = "/dispense")
    public ResponseEntity<ReservationDTO> dispensingMedicine(@RequestBody ReservationDTO reservationDTO){

        reservationService.dispensingMedicine(reservationDTO);

        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }


}
