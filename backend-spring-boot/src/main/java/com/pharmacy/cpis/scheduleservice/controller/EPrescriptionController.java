package com.pharmacy.cpis.scheduleservice.controller;

import com.pharmacy.cpis.scheduleservice.dto.prescription.EPrescriptionCreateDTO;
import com.pharmacy.cpis.scheduleservice.dto.prescription.EPrescriptionReadDTO;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.service.IEPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/prescriptions")
public class EPrescriptionController {

    @Autowired
    private IEPrescriptionService prescriptionService;

    @PostMapping(value = "/save")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<EPrescriptionReadDTO> getPredefinedByPharmacy(@RequestBody EPrescriptionCreateDTO ePrescriptionCreateDTO) {
        EPrescription ePrescription = prescriptionService.savePrescription(ePrescriptionCreateDTO);
        return new ResponseEntity<>(new EPrescriptionReadDTO(ePrescription),HttpStatus.OK);
    }

}
