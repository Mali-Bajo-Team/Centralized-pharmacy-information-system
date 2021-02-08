package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import com.pharmacy.cpis.userservice.repository.IComplaintRepository;
import com.pharmacy.cpis.userservice.service.IComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService implements IComplaintService {

    @Autowired
    private IComplaintRepository complaintRepository;

    @Override
    public List<Complaint> findAllComplaints() {
        return complaintRepository.findAll();
    }
}
