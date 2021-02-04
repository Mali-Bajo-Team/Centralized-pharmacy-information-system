package com.pharmacy.cpis.pharmacyservice.controller;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/pharmacies")
public class PharmacyController {

    @Autowired
    private IPharmacyService pharmacyService;

    // TODO: Decide how to name this type of routes
    @GetMapping("/all")
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies(){

        List<Pharmacy> pharmacies=pharmacyService.findAll();

        //convert pharmacies to DTOs
        List<PharmacyDTO> pharmaciesDTO= new ArrayList<>();
        for (Pharmacy pharmacy : pharmacies) {
            PharmacyDTO dtoPharmacy = new PharmacyDTO(pharmacy);
            pharmaciesDTO.add(dtoPharmacy);
        }

        return new ResponseEntity<>(pharmaciesDTO,HttpStatus.OK);
    }


}
