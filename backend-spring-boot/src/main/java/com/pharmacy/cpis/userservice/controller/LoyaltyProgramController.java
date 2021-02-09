package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.userservice.dto.LoyaltyProgramDTO;
import com.pharmacy.cpis.userservice.dto.UserCategoryDTO;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/loyaltyprograms")
public class LoyaltyProgramController {

    @Autowired
    private ILoyaltyProgramService loyaltyProgramService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoyaltyProgramDTO>> getLoyaltyPrograms(){
        List<LoyaltyProgramDTO> loyaltyProgramDTOS = new ArrayList<>();
        for(LoyaltyProgram loyaltyProgram : loyaltyProgramService.findAll())
            loyaltyProgramDTOS.add(new LoyaltyProgramDTO(loyaltyProgram));
        return new ResponseEntity<>(loyaltyProgramDTOS, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoyaltyProgramDTO> updateLoyaltyProgram(@RequestBody LoyaltyProgramDTO loyaltyProgramDTO){
        LoyaltyProgram loyaltyProgram = loyaltyProgramService.updateLoyaltyProgram(loyaltyProgramDTO);
        return new ResponseEntity<>(new LoyaltyProgramDTO(loyaltyProgram),HttpStatus.OK);
    }

    @PostMapping(value = "/categories", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserCategoryDTO> addUserCategory(@RequestBody UserCategoryDTO userCategoryDTO){
        UserCategory userCategory = loyaltyProgramService.saveCategory(userCategoryDTO);
        return new ResponseEntity<>(new UserCategoryDTO(userCategory) ,HttpStatus.OK);
    }

    @PutMapping(value = "/categories", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserCategoryDTO> updateUserCategory(@RequestBody UserCategoryDTO userCategoryDTO){
        UserCategory userCategory = loyaltyProgramService.updateCategory(userCategoryDTO);
        return new ResponseEntity<>(new UserCategoryDTO(userCategory) ,HttpStatus.OK);
    }

    @DeleteMapping(value = "/categories", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserCategory(@RequestBody UserCategoryDTO userCategoryDTO){
        loyaltyProgramService.removeCategory(userCategoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
