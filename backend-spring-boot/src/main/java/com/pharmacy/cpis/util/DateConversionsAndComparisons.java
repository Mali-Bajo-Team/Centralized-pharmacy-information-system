package com.pharmacy.cpis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class DateConversionsAndComparisons {

	// number ranges from 1 (Sunday) to 7 (Saturday).
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int compareTimesWithoutDates(Date d1, Date d2) {
		return getTime(d1).compareTo(getTime(d2));
	}

	public static long compareDatesWithoutTime(Date d1, Date d2) {
		return getDate(d1).compareTo(getDate(d2));
	}

	// Convert 2021-02-12 13:55:00 to Util
	public static Date getUtilDate(String stringTime) {
		Date convertedUtilDate = null;
		try {
			convertedUtilDate = new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(stringTime);
		} catch (ParseException e) {
			System.out.println("Util date comparassion error");
		}
		return convertedUtilDate;
	}

	public static int compareDates(Date d1, Date d2) {
		return d1.compareTo(d2);
	}

	public static Iterable<Date> getDatesBetween(Date start, Date end) {
		Collection<Date> retVal = new ArrayList<Date>();

		Date current = getDate(start);

		while (DateConversionsAndComparisons.compareDatesWithoutTime(current, end) <= 0) {
			retVal.add(current);
			current = new Date(current.getTime() + 24 * 60 * 60 * 1000L);
		}

		return retVal;
	}

	public static boolean overlapsWithoutTime(DateRange dr1, DateRange dr2) {
		if (compareDatesWithoutTime(dr1.getStart(), dr2.getStart()) <= 0
				&& compareDatesWithoutTime(dr1.getEnd(), dr2.getStart()) >= 0)
			return true;

		if (compareDatesWithoutTime(dr1.getStart(), dr2.getEnd()) <= 0
				&& compareDatesWithoutTime(dr1.getEnd(), dr2.getEnd()) >= 0)
			return true;

		return false;
	}

	public static boolean overlaps(DateRange dr1, DateRange dr2) {
		if (dr1.getStart().compareTo(dr2.getStart()) <= 0 && dr1.getEnd().compareTo(dr2.getStart()) > 0)
			return true;

		if (dr1.getStart().compareTo(dr2.getEnd()) < 0 && dr1.getEnd().compareTo(dr2.getEnd()) >= 0)
			return true;

		return false;
	}

	public static boolean overlapsWithoutDates(DateRange dr1, DateRange dr2) {
		if (compareTimesWithoutDates(dr1.getStart(), dr2.getStart()) <= 0
				&& compareTimesWithoutDates(dr1.getEnd(), dr2.getStart()) > 0)
			return true;

		if (compareTimesWithoutDates(dr1.getStart(), dr2.getEnd()) < 0
				&& compareTimesWithoutDates(dr1.getEnd(), dr2.getEnd()) >= 0)
			return true;

		return false;
	}

	public static Date getTime(Date dateTime) {
		Date date = getDate(dateTime);
		return new Date(dateTime.getTime() - date.getTime());
	}

	public static Date getDate(Date dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		Calendar newCal = Calendar.getInstance();
		newCal.clear();
		newCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		return newCal.getTime();
	}

}
