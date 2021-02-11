package com.pharmacy.cpis.scheduleservice.controller;

import com.pharmacy.cpis.scheduleservice.dto.ConsultationDTO;
import com.pharmacy.cpis.scheduleservice.dto.EPrescriptionCreateDTO;
import com.pharmacy.cpis.scheduleservice.dto.EPrescriptionReadDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.service.IEPrescriptionService;
import com.pharmacy.cpis.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/prescriptions")
public class EPrescriptionController {

    @Autowired
    private IEPrescriptionService prescriptionService;

    @PostMapping(value = "/save")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<EPrescriptionReadDTO> getPredefinedByPharmacy(@RequestBody EPrescriptionCreateDTO ePrescriptionCreateDTO) {
        EPrescription ePrescription = prescriptionService.savePrescription(ePrescriptionCreateDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
