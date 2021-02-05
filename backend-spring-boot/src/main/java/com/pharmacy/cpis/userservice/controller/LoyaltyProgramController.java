package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.userservice.dto.LoyaltyProgramDTO;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/loyaltyprograms")
public class LoyaltyProgramController {

    @Autowired
    private ILoyaltyProgramService loyaltyProgramService;

    @GetMapping()
    public ResponseEntity<List<LoyaltyProgramDTO>> getLoyaltyPrograms(){

        List<LoyaltyProgram> loyaltyPrograms = loyaltyProgramService.findAll();

        // convert loyalties to dto
        List<LoyaltyProgramDTO> loyaltyProgramDTOS = new ArrayList<>();
        for(LoyaltyProgram loyaltyProgram : loyaltyPrograms){
            LoyaltyProgramDTO loyaltyProgramDTO = new LoyaltyProgramDTO(loyaltyProgram);
            loyaltyProgramDTOS.add(loyaltyProgramDTO);
        }

        return new ResponseEntity<>(loyaltyProgramDTOS, HttpStatus.OK);
    }
}
