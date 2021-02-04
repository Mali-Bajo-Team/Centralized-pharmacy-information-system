package com.pharmacy.cpis.drugservice.controller;

import com.pharmacy.cpis.drugservice.dto.DrugRegisterDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.service.IDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/drugs")
public class DrugController {

    @Autowired
    private IDrugService drugService;

    @PostMapping(value = "/register", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Drug> addDrug(@RequestBody DrugRegisterDTO drugRegisterDTO) {
        Drug addedPharmacy = drugService.registerDrug(drugRegisterDTO);
        return new ResponseEntity<>(addedPharmacy, HttpStatus.CREATED);
    }
}
