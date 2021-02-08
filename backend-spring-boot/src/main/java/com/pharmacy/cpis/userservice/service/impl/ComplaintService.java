package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.dto.ComplaintDTO;
import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import com.pharmacy.cpis.userservice.repository.IComplaintRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IComplaintService;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ComplaintService implements IComplaintService {

    @Autowired
    private IComplaintRepository complaintRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<Complaint> findAllComplaints() {
        return complaintRepository.findAll();
    }

    @Override
    public Complaint updateComplaintResponse(ComplaintDTO complaintDTO) {
        Complaint complaint = getComplaint(complaintDTO);
        complaint.setResponse(complaintDTO.getResponse());
        return complaintRepository.save(complaint);
    }

    private Complaint getComplaint(ComplaintDTO complaintDTO) {
        Long consultantId = null;
        Long pharmacyId = null;
        Long creatorId = complaintDTO.getCreator().getId();
        Date creationTimestamp = complaintDTO.getCreationTimestamp();
        Complaint complaint;
        if(complaintDTO.getConsultant() != null)
            consultantId = userRepository.findByEmail(complaintDTO.getConsultant().getEmail()).getPerson().getId();
        if(complaintDTO.getPharmacy() != null)
            pharmacyId = complaintDTO.getPharmacy().getId();
        if(consultantId != null){
            complaint = complaintRepository.findByCreatorIdAndConsultantIdAndCreationTimestamp(creatorId, consultantId, creationTimestamp);
        }else if(pharmacyId != null){
            complaint = complaintRepository.findByCreatorIdAndPharmacyIdAndCreationTimestamp(creatorId, pharmacyId, creationTimestamp);
        }else{
            throw new PSBadRequestException("Not found that complaint");
        }
        return complaint;
    }
}
