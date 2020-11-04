package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import util.TimeFrom;

public class DateHandler {

	public static List<String> getFiveMonthsFromNow() {
		List<String> monthList = new ArrayList<String>();

		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		
		for(int i = 4; i >= 0; --i) {
			c.setTime(dt);
			c.add(Calendar.MONTH, -i);
			
			monthList.add(new SimpleDateFormat("MMMM").format(c.getTime()));
		}
		
		return monthList;
	}
	
	public static List<Date> getDateTillNow(TimeFrom timeUnit, int numberFrom) {
		List<Date> dateList = new ArrayList<Date>();
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		
		if(timeUnit == TimeFrom.DAILY) {
			for(int i = numberFrom - 1; i >= 0; i--) {
				c.setTime(dt);
				c.add(Calendar.DAY_OF_MONTH, -i);
				
				dateList.add(c.getTime());
			}
		}
		
		return dateList;
	}
	
	public static String getTimeFromNow(Timestamp time) {
		long difference = Timestamp.from(Instant.now()).getTime() - time.getTime();
		
		long days = TimeUnit.MILLISECONDS.toDays(difference);
		long hours = TimeUnit.MILLISECONDS.toHours(difference) % 24;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(difference) % 60;
		
		String result = "";
		
		if(days != 0) {
			if(days == 1)
				result += days + " day ";
			
			else
				result += days + " days ";
		}
		
		if(hours != 0) {
			if(hours == 1)
				result += hours + " hour ";
			
			else
				result += hours + " hours ";
		}
		
		if(minutes != 0) {
			if(minutes == 1)
				result += minutes + " minute ";
			
			else
				result += minutes + " minutes ";
		}
		
		if(days == 0 && hours == 0 && minutes == 0) {
			result += "Just a while ";
		}
		
		result += "ago.";
		
		return result;
	}
	
}
