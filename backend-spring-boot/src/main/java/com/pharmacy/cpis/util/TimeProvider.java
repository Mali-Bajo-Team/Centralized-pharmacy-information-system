package com.pharmacy.cpis.util;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TimeProvider {

	public Date currentDateAndTime() {
		return new Date();
	}

	public Date currentDate() {
		return DateConversionsAndComparisons.getDate(new Date());
	}

}
