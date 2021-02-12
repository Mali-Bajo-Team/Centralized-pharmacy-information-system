package com.pharmacy.cpis.pharmacyservice.controller;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.cpis.pharmacyservice.dto.statistics.DateRangeDTO;
import com.pharmacy.cpis.pharmacyservice.dto.statistics.MQYReportDTO;
import com.pharmacy.cpis.pharmacyservice.dto.statistics.ProfitReportDTO;
import com.pharmacy.cpis.pharmacyservice.dto.statistics.YearRangeDTO;
import com.pharmacy.cpis.pharmacyservice.service.IReportsService;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.util.aspects.EmployeeAccountActive;

@RestController
@RequestMapping(value = "api/reports")
public class ReportsController {
	@Autowired
	private IReportsService reportsService;

	@PostMapping("/profit")
	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@EmployeeAccountActive
	public ResponseEntity<ProfitReportDTO> getProfitReport(@RequestBody @Valid DateRangeDTO range,
			Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();
		Long pharmachy = user.getPharmacyId();
		LocalDate start = new LocalDate(range.getStart());
		LocalDate end = new LocalDate(range.getEnd());
		ProfitReportDTO ret = reportsService.getProfitReport(pharmachy, start, end);
		return new ResponseEntity<ProfitReportDTO>(ret, HttpStatus.OK);
	}

	@PostMapping("/druguse")
	public ResponseEntity<MQYReportDTO> getDrugUseReport(@RequestBody @Valid YearRangeDTO range,
			Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();
		Long pharmachy = user.getPharmacyId();
		MQYReportDTO ret = reportsService.getDrugUseReport(pharmachy, range.getStart(), range.getEnd());
		return new ResponseEntity<MQYReportDTO>(ret, HttpStatus.OK);
	}

	@PostMapping("/consults")
	public ResponseEntity<MQYReportDTO> getConsultationsReport(@RequestBody @Valid YearRangeDTO range,
			Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();
		Long pharmachy = user.getPharmacyId();
		MQYReportDTO ret = reportsService.getConsultationsReport(pharmachy, range.getStart(), range.getEnd());
		return new ResponseEntity<MQYReportDTO>(ret, HttpStatus.OK);
	}
}