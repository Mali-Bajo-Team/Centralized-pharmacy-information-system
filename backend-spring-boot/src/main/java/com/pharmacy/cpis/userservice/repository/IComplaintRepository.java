package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComplaintRepository extends JpaRepository<Complaint, Long> {
}
