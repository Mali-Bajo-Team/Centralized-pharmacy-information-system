package com.pharmacy.cpis.scheduleservice.controller;

import com.pharmacy.cpis.scheduleservice.dto.AddVacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.dto.DrugRecommendationDTO;
import com.pharmacy.cpis.scheduleservice.service.IDrugRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/drugrecommendation")
public class DrugRecommendationController {
    @Autowired
    private IDrugRecommendationService drugRecommendationService;

    @PostMapping("/recommend")
    @PreAuthorize("hasRole('PHARMACIST') || hasRole('DERMATOLOGIST')")
    public ResponseEntity<DrugRecommendationDTO> recommendDrug(
            @RequestBody DrugRecommendationDTO drugRecommendationDTO) {

        DrugRecommendationDTO drugRecommendationDTOForReturn = new DrugRecommendationDTO();
        drugRecommendationDTOForReturn = drugRecommendationService.recommendDrug(drugRecommendationDTO);

        return new ResponseEntity<>(drugRecommendationDTOForReturn, HttpStatus.OK);
    }

    @PostMapping("/checkbeforerecommend")
    @PreAuthorize("hasRole('PHARMACIST') || hasRole('DERMATOLOGIST')")
    public ResponseEntity<DrugRecommendationDTO> checkIsDrugAvailable(
            @RequestBody DrugRecommendationDTO drugRecommendationDTO) {

        DrugRecommendationDTO drugRecommendationDTOForReturn = new DrugRecommendationDTO();
        drugRecommendationDTOForReturn = drugRecommendationService.isDrugAvailable(drugRecommendationDTO);

        return new ResponseEntity<>(drugRecommendationDTOForReturn, HttpStatus.OK);
    }

}
