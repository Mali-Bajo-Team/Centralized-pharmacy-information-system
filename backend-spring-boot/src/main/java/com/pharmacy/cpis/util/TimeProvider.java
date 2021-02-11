package com.pharmacy.cpis.util;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TimeProvider {

	public Date currentDateAndTime() {
		return new Date();
	}

	public Date currentDate() {
		Date withTime = currentDateAndTime();
		return new Date(withTime.getTime() - withTime.getTime() % (24 * 60 * 60 * 1000L));
	}

}
