package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.model.complaints.Complaint;

import java.util.Collection;
import java.util.List;

public interface IComplaintService {
    List<Complaint> findAllComplaints();
}
