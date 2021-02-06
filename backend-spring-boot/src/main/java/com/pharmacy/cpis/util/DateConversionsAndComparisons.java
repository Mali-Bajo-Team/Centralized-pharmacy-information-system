package com.pharmacy.cpis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConversionsAndComparisons {

    //number ranges from 1 (Sunday) to 7 (Saturday).
    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int compareTimesWithoutDates(Date d1, Date d2)
    {
        int t1;
        int t2;

        t1 = (int) (d1.getTime() % (24*60*60*1000L));
        t2 = (int) (d2.getTime() % (24*60*60*1000L));
        return (t1 - t2);
    }

    //Convert 2021-02-12 13:55:00 to Util
    public static Date getUtilDate(String stringTime) {
        Date convertedUtilDate = null;
        try {
            convertedUtilDate=new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(stringTime);
        } catch (ParseException e) {
            System.out.println("Util date comparassion error");
        }
        return convertedUtilDate;
    }

    //d1>d2 => >0
    //d1<d2 => <0
    //d1==d2 => =0
    public static int compareDates(Date d1, Date d2)
    {
        int t1;
        int t2;

        t1 = (int) (d1.getTime());
        t2 = (int) (d2.getTime());
        System.out.println(t1-t2);
        return (t1 - t2);
    }


}
