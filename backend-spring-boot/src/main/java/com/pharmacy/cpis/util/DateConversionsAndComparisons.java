package com.pharmacy.cpis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class DateConversionsAndComparisons {

	// number ranges from 1 (Sunday) to 7 (Saturday).
	public static int getDayNumberOld(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int compareTimesWithoutDates(Date d1, Date d2) {
		int t1;
		int t2;

		t1 = (int) (d1.getTime() % (24 * 60 * 60 * 1000L));
		t2 = (int) (d2.getTime() % (24 * 60 * 60 * 1000L));
		return (t1 - t2);
	}

	public static long compareDatesWithoutTime(Date d1, Date d2) {
		long t1;
		long t2;

		t1 = d1.getTime() - d1.getTime() % (24 * 60 * 60 * 1000L);
		t2 = d2.getTime() - d2.getTime() % (24 * 60 * 60 * 1000L);
		return (t1 - t2);
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

	// d1>d2 => >0
	// d1<d2 => <0
	// d1==d2 => =0
	public static int compareDates(Date d1, Date d2) {
		int t1;
		int t2;

		t1 = (int) (d1.getTime());
		t2 = (int) (d2.getTime());
		System.out.println(t1 - t2);
		return (t1 - t2);
	}

	public static Iterable<Date> getDatesBetween(Date start, Date end) {
		Collection<Date> retVal = new ArrayList<Date>();

		Date current = new Date(start.getTime() - start.getTime() % (24 * 60 * 60 * 1000L));
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

}
