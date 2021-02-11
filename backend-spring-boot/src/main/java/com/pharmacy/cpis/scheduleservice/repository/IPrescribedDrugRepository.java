package com.pharmacy.cpis.scheduleservice.repository;

import com.pharmacy.cpis.scheduleservice.model.prescriptions.PrescribedDrug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrescribedDrugRepository extends JpaRepository<PrescribedDrug, Long> {
}
