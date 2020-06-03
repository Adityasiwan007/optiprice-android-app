package com.ril.digitalwardrobeAI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateFormat {

    public static String DATE_FORMAT="EEE MMM d HH:mm:ss z yyyy";

    public static  String getFormattedDate(Date ourDate,String format)
    {
        String dateString;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate.toString());

            SimpleDateFormat dateFormatter = new SimpleDateFormat(format); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            dateString = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        }
        catch (Exception e)
        {
            dateString = "00-00-0000 00:00";
        }
        return dateString;
    }

    public static Date setTime( final Date date, final int hourOfDay, final int minute, final int second, final int ms )
    {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( date );
        gc.set( Calendar.HOUR_OF_DAY, hourOfDay );
        gc.set( Calendar.MINUTE, minute );
        gc.set( Calendar.SECOND, second );
        gc.set( Calendar.MILLISECOND, ms );
        return gc.getTime();
    }

}
