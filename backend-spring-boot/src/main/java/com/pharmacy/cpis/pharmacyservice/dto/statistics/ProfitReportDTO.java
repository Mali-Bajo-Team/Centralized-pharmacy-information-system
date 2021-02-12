package com.pharmacy.cpis.pharmacyservice.dto.statistics;

import java.util.ArrayList;
import java.util.List;

public class ProfitReportDTO {
	private List<String> labels;
	private List<Double> values;

	public ProfitReportDTO() {
		labels = new ArrayList<String>();
		values = new ArrayList<Double>();
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}
}
