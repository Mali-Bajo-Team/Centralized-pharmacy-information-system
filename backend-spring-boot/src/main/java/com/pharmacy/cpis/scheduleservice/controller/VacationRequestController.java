package com.pharmacy.cpis.scheduleservice.controller;
import com.pharmacy.cpis.scheduleservice.dto.VacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.service.IVacationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/vacationrequst")
public class VacationRequestController {
    @Autowired
    private IVacationRequestService vacationRequestService;

    @PostMapping("/createrequest")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<VacationRequestDTO> getAllConsultationsForConsultant(@RequestBody VacationRequestDTO vacationRequestDTO) {

        VacationRequestDTO vacationRequest = vacationRequestService.createVacationRequest(vacationRequestDTO);

        return new ResponseEntity<>(vacationRequest, HttpStatus.OK);
    }
}
