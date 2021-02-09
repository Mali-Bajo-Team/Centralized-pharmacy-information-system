package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.dto.ComplaintDTO;
import com.pharmacy.cpis.userservice.dto.CreateComplaintDTO;
import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import com.pharmacy.cpis.userservice.model.users.Patient;

import java.util.Collection;
import java.util.List;

public interface IComplaintService {
    List<Complaint> findAllComplaints();

    Complaint updateComplaintResponse(ComplaintDTO complaintDTO);

    Complaint createComplaint(CreateComplaintDTO complaintDTO);

    List<Pharmacy> findAllPatientPharmacies(Patient patient);
}
