package com.pharmacy.cpis.scheduleservice.repository;

import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsultationReportRepository  extends JpaRepository<ConsultationReport, Long> {
}
