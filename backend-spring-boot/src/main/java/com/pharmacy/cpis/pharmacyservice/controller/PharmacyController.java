package com.pharmacy.cpis.pharmacyservice.controller;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDTO;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyRegisterDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/pharmacies")
public class PharmacyController {

    @Autowired
    private IPharmacyService pharmacyService;

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

    @PostMapping(value = "/register", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pharmacy> addPharmacy(@RequestBody PharmacyRegisterDTO pharmacyRegisterDTO) {
        Pharmacy addedPharmacy = pharmacyService.registerPharmacy(pharmacyRegisterDTO);
        return new ResponseEntity<>(addedPharmacy, HttpStatus.CREATED);
    }


}
