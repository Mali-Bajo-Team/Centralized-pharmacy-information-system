package com.pharmacy.cpis.pharmacyservice.dto.statistics;

import java.util.ArrayList;
import java.util.List;

public class MQYReportDTO {
	private List<String> monthLabels = new ArrayList<String>();
	private List<Integer> monthValues = new ArrayList<Integer>();
	private List<String> quartalLabels = new ArrayList<String>();
	private List<Integer> quartalValues = new ArrayList<Integer>();
	private List<String> yearLabels = new ArrayList<String>();
	private List<Integer> yearValues = new ArrayList<Integer>();

	public List<String> getMonthLabels() {
		return monthLabels;
	}

	public void setMonthLabels(List<String> monthLabels) {
		this.monthLabels = monthLabels;
	}

	public List<Integer> getMonthValues() {
		return monthValues;
	}

	public void setMonthValues(List<Integer> monthValues) {
		this.monthValues = monthValues;
	}

	public List<String> getQuartalLabels() {
		return quartalLabels;
	}

	public void setQuartalLabels(List<String> quartalLabels) {
		this.quartalLabels = quartalLabels;
	}

	public List<Integer> getQuartalValues() {
		return quartalValues;
	}

	public void setQuartalValues(List<Integer> quartalValues) {
		this.quartalValues = quartalValues;
	}

	public List<String> getYearLabels() {
		return yearLabels;
	}

	public void setYearLabels(List<String> yearLabels) {
		this.yearLabels = yearLabels;
	}

	public List<Integer> getYearValues() {
		return yearValues;
	}

	public void setYearValues(List<Integer> yearValues) {
		this.yearValues = yearValues;
	}

}