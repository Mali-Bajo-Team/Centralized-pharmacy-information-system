package com.pharmacy.cpis.userservice.controller;

import com.pharmacy.cpis.userservice.dto.ComplaintDTO;
import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import com.pharmacy.cpis.userservice.service.IComplaintService;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/complaints")
public class ComplaintController {

    @Autowired
    private IComplaintService complaintService;

    @GetMapping()
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints(){
        List<ComplaintDTO> complaintDTOS = new ArrayList<>();
        for(Complaint complaint:  complaintService.findAllComplaints())
            complaintDTOS.add(new ComplaintDTO(complaint));
        return new ResponseEntity<>(complaintDTOS, HttpStatus.OK);
    }

    @PutMapping()
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplaintDTO> updateComplaintResponse(@RequestBody ComplaintDTO complaintDTO){
        Complaint complaint = complaintService.updateComplaintResponse(complaintDTO);
        return new ResponseEntity<>(new ComplaintDTO(complaint),HttpStatus.OK);
    }

}
