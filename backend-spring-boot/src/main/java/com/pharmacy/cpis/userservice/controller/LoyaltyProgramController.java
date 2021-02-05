package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/loyaltyprograms")
public class LoyaltyProgramController {

    @Autowired
    private ILoyaltyProgramService loyaltyProgramService;

    // TODO: RESITI ONO GDE LOYALTY PROGRAM IMA KATEGORIJU A ONDA ONA LOYALTY PROGRAM I TAKO BESKONACNO
    @GetMapping()
    public ResponseEntity<List<LoyaltyProgram>> getLoyaltyPrograms(){
        return new ResponseEntity<>(loyaltyProgramService.findAll(), HttpStatus.OK);
    }
}
