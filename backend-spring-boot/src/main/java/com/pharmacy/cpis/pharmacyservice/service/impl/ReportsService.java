package com.pharmacy.cpis.pharmacyservice.service.impl;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.drugservice.repository.IDrugPurchaseRepository;
import com.pharmacy.cpis.pharmacyservice.dto.statistics.MQYReportDTO;
import com.pharmacy.cpis.pharmacyservice.dto.statistics.ProfitReportDTO;
import com.pharmacy.cpis.pharmacyservice.service.IReportsService;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;

@Service
public class ReportsService implements IReportsService {
	@Autowired
	private IConsultationRepository consultationRepo;
	@Autowired
	private IDrugPurchaseRepository drugRepo;

	@Override
	public ProfitReportDTO getProfitReport(Long id, LocalDate start, LocalDate end) {
		ProfitReportDTO ret = new ProfitReportDTO();
		for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
			ret.getLabels().add(date.toString());
			Double value = 0.0;
			Double consultationsProfit = consultationRepo.getProfitForDate(id, date.getYear(), date.getMonthOfYear(),
					date.getDayOfMonth());
			if (consultationsProfit != null) {
				value += consultationsProfit;
			}
			Double saleProfit = drugRepo.getProfitForDate(id, date.getYear(), date.getMonthOfYear(),
					date.getDayOfMonth());
			if (saleProfit != null) {
				value += saleProfit;
			}
			ret.getValues().add(value);
		}
		return ret;
	}

	@Override
	public MQYReportDTO getConsultationsReport(Long id, int start, int end) {
		MQYReportDTO ret = new MQYReportDTO();
		// monthly
		for (int year = start; year <= end; ++year) {
			for (int month = 1; month <= 12; ++month) {
				ret.getMonthLabels().add(month + " - " + year);
				Integer count = consultationRepo.getConsultationsCount(id, month, year);
				ret.getMonthValues().add(count);
			}
		}
		createQYbasedOnM(ret, start, end);
		return ret;
	}

	@Override
	public MQYReportDTO getDrugUseReport(Long id, int start, int end) {
		MQYReportDTO ret = new MQYReportDTO();
		// monthly
		for (int year = start; year <= end; ++year) {
			for (int month = 1; month <= 12; ++month) {
				ret.getMonthLabels().add(month + " - " + year);
				Integer count = drugRepo.getAmountUsed(id, month, year);
				if (count != null) {
					ret.getMonthValues().add(count);
				} else {
					ret.getMonthValues().add(0);
				}
			}
		}
		createQYbasedOnM(ret, start, end);
		return ret;
	}

	private int sumInRange(List<Integer> list, int start, int end) {
		int sum = 0;
		for (int i = start; i < end; ++i) {
			sum += list.get(i);
		}
		return sum;
	}

	private void createQYbasedOnM(MQYReportDTO report, int start, int end) {
		int i = 0;
		// quarterly
		for (int year = start; year <= end; ++year) {
			for (int q = 1; q <= 4; ++q) {
				report.getQuartalLabels().add(q + " - " + year);
				report.getQuartalValues().add(sumInRange(report.getMonthValues(), i, i + 3));
				i += 3;
			}
		}
		// yearly
		i = 0;
		for (int year = start; year <= end; ++year) {
			report.getYearLabels().add(year + "");
			report.getYearValues().add(sumInRange(report.getMonthValues(), i, i + 12));
			i += 12;
		}
	}
}
