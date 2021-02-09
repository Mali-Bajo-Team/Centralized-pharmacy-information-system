package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.userservice.dto.ComplaintDTO;
import com.pharmacy.cpis.userservice.dto.CreateComplaintDTO;
import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IComplaintRepository;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IComplaintService;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ComplaintService implements IComplaintService {

    @Autowired
    private IComplaintRepository complaintRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IConsultantRepository consultantRepository;

    @Autowired
    private IPharmacyRepository pharmacyRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IConsultationService consultationService;

    @Autowired
    private IReservationService reservationService;

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

    @Override
    public Complaint createComplaint(CreateComplaintDTO complaintDTO) {
        Complaint complaint = new Complaint();
        complaint.setResponse("Waiting on reply");
        complaint.setCreationTimestamp(new Date());
        complaint.setContent(complaintDTO.getContent());
        Long creatorId = userRepository.findByEmail(complaintDTO.getPatientEmail()).getPerson().getId();
        Patient creator = patientRepository.findById(creatorId).orElse(null);

        if(creator == null) throw new PSBadRequestException("There is no patient with that data");
        complaint.setCreator(creator);

        if(complaintDTO.getConsultantEmail() != null && !complaintDTO.getConsultantEmail().equals("")){
            Long consultantId = userRepository.findByEmail(complaintDTO.getPatientEmail()).getPerson().getId();
            if(consultantId == null) throw new PSBadRequestException("There is no consultant with that data");
            Consultant consultant = consultantRepository.findById(consultantId).orElse(null);
            if(consultant == null) throw new PSBadRequestException("There is no consultant with that data");
            complaint.setConsultant(consultant);
        }
        if(complaintDTO.getPharmacyId() != null){
            Pharmacy pharmacy = pharmacyRepository.findById(complaintDTO.getPharmacyId()).orElse(null);
            if(pharmacy == null) throw new PSBadRequestException("There is no pharmacy with that data");
            complaint.setPharmacy(pharmacy);
        }
        return complaintRepository.save(complaint);
    }

    @Override
    /*
      Return all pharmacies with which the patient had contact points:
      consultations, drug reservation, ePrescription
     */
    public List<Pharmacy> findAllPatientPharmacies(Patient patient) {
        List<Pharmacy> allPatientPharmacies = new ArrayList<>(consultationService.findAllPatientPharmacies(patient));
        for(Pharmacy pharmacy : reservationService.findAllPatientPharmacies(patient)){
            boolean alreadyAdded = false;
            for(Pharmacy alreadyAddedPharmacy : allPatientPharmacies){
                if(alreadyAddedPharmacy.getId().equals(pharmacy.getId())){
                    alreadyAdded = true;
                    break;
                }
            }
            if(!alreadyAdded)
                allPatientPharmacies.add(pharmacy);
        }

        return allPatientPharmacies;
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
