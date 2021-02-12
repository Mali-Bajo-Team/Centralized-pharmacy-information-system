package com.pharmacy.cpis.pharmacyservice.service;

import org.joda.time.LocalDate;

import com.pharmacy.cpis.pharmacyservice.dto.statistics.MQYReportDTO;
import com.pharmacy.cpis.pharmacyservice.dto.statistics.ProfitReportDTO;

public interface IReportsService {

	ProfitReportDTO getProfitReport(Long id, LocalDate start, LocalDate end);

	MQYReportDTO getConsultationsReport(Long id, int start, int end);

	MQYReportDTO getDrugUseReport(Long id, int start, int end);

}