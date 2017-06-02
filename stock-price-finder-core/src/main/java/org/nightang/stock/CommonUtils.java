package org.nightang.stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CommonUtils {

	private SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyyMMdd");

	private SimpleDateFormat dateDBStringFormat = new SimpleDateFormat("yyyyMMdd");
	
	public Date getDateBeforeToday(int daysDiff) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, -daysDiff);
		return cal.getTime();
	}

	public String getDateDBStringBeforeToday(int daysDiff) {
		return dateDBStringFormat.format(getDateBeforeToday(daysDiff));
	}
	
	public Date truncTimeUnit(Date date) {
		String str = dateOnlyFormat.format(date);
		try {
			return dateOnlyFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}		
	}

}
