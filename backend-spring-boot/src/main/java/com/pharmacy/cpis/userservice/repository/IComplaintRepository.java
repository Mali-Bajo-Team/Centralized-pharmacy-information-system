package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface IComplaintRepository extends JpaRepository<Complaint, Long> {
    Complaint findByCreatorIdAndConsultantIdAndCreationTimestamp(Long creatorId, Long consultantId, Date creationTimestamp);
    Complaint findByCreatorIdAndPharmacyIdAndCreationTimestamp(Long creatorId, Long pharmacyId, Date creationTimestamp);
}
