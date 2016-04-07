package logic.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;

/* @@author A0112184R
 * This class contains the getInstance() method to generate a Task object
 * and the comparator for tasks
 */
public class TaskUtil {
	
	private static final String LINE_SEPARATOR = ":";
	private static final String NULL_TIME = "-";
	private static final String FIELD_SEPARATOR = ";";
	private static final SimpleDateFormat STORAGE_FORMAT = new SimpleDateFormat("HH:mm yyyyMMdd", Locale.ENGLISH);
	private static final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

	public static class TaskComparator implements Comparator<Task> {
		public int compare(Task task1, Task task2) {
			return task1.compareTo(task2);
		}
	}
	
	public static Task getInstance(String title, Calendar startDate, Calendar endDate, int period) {
		if (period == 0) {
			return new Task(title, startDate, endDate);
		} else {
			return new RecurringTask(title, startDate, endDate, period);
		}
	}
	
	public static Task getInstance(String title, Calendar endDate, int period) {
		return getInstance(title, null, endDate, period);
	}
	
	public static Task getInstance(String title, int period) {
		return getInstance(title, null, period);
	}
	
	public static Task getInstance(String title) {
		return getInstance(title, 0);
	}
	
	public static Task getInstance(String title, Calendar startDate, Calendar endDate) {
		return new Task(title, startDate, endDate);
	}
	
	public static Task getInstance(String title, Calendar endDate) {
		return new Task(title, null, endDate);
	}
	
	// @@author A0134185H
	public static Task parseFromStorage(String entry) throws IllegalArgumentException {
		
		String[] parts = entry.split(FIELD_SEPARATOR);
		
		String titleString, startString, endString, doneString, periodString;
		Calendar startDate;
		Calendar endDate;
		boolean isDone;
		int period;

		titleString = findValidLine("title", parts);
		startString = findValidLine("start", parts);
		endString = findValidLine("end", parts);
		doneString = findValidLine("status", parts) ;
		periodString = findValidLine("period", parts);
		
		if (titleString == null) {
			throw new IllegalArgumentException("not a proper entry");
		}
		startDate = stringToCalendar(startString);
		endDate = stringToCalendar(endString);
		isDone = (doneString == null || doneString.equalsIgnoreCase("incomplete"))? false: true;
		period = (periodString == null)? 0: Integer.parseInt(periodString);
		
		Task result = getInstance(titleString, startDate, endDate, period);
		result.setDone(isDone);
		return result;
	}

	private static String findValidLine(String header, String[] lines) {
		String fieldString = null;
		for (String line: lines) {
			String[] lineParts = line.split(LINE_SEPARATOR);
			if (lineParts[0].trim().toLowerCase().equals(header)) {
				fieldString = lineParts[1].trim();
			} 
		}
		return fieldString;
	}
	
	public static Calendar stringToCalendar(String dateString) {
		if (dateString == null) {
			return null;
		}
		try {
			Calendar result = Calendar.getInstance();
			result.setTime(STORAGE_FORMAT.parse(dateString));
			return result;
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String calendarToString(Calendar date, DateFormat format) {
		if (date == null) {
			return NULL_TIME;
		} else {
			return format.format(date.getTime());
		}
	}
	
	public static String convertToStorage(Task task) {
		StringBuilder sb = new StringBuilder();
		sb.append("title: " + task.getTitle() + FIELD_SEPARATOR + "\r\n");
		String startDateString, endDateString, statusString;
		startDateString = calendarToString(task.getStartDate(), STORAGE_FORMAT);
		endDateString = calendarToString(task.getEndDate(), STORAGE_FORMAT);
		statusString = (task.isDone())? "completed":"incomplete";
		sb.append("start: " + startDateString + FIELD_SEPARATOR + "\r\n");
		sb.append("end: " + endDateString + FIELD_SEPARATOR + "\r\n");
		sb.append("status: " + statusString + FIELD_SEPARATOR + "\r\n");
		return sb.toString();
	}
	
	public static String[] toStringArray(Task task) {
		String isDoneString = task.isDone() ? "complete" : "incomplete";
		String titleString = task.getTitle();
		String startString = calendarToString(task.getStartDate(), DISPLAY_FORMAT);
		String endString = calendarToString(task.getEndDate(), DISPLAY_FORMAT);
		String periodString = (task instanceof RecurringTask)? ((RecurringTask) task).getPeriodString(): "-";
		
		String[] result = {isDoneString, titleString, startString, endString, periodString};
		return result;
	}
	
	/* @@author A0112184R-reused
	 * Source: http://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-in-java
	 */
	public static int daysBetween(Calendar day1, Calendar day2){
	    Calendar dayOne = (Calendar) day1.clone(),
	            dayTwo = (Calendar) day2.clone();

	    if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
	        return dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR);
	    } else {
	        if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
	            return -1;
	        }
	        int extraDays = 0;

	        int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

	        while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
	            dayOne.add(Calendar.YEAR, -1);
	            // getActualMaximum() important for leap years
	            extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
	        }

	        return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays ;
	    }
	}
	
	// @author A0112184R
	
	public static boolean isBeforeDay(Calendar date1, Calendar date2) {
		return (DateUtils.truncatedCompareTo(date1, date2, Calendar.DATE) < 0);
	}
	
	public static boolean isAfterDay(Calendar date1, Calendar date2) {
		return (DateUtils.truncatedCompareTo(date1, date2, Calendar.DATE) > 0);
	}

	public static boolean willOccur(Task task, Calendar date) {
		if (task.getStartDate() == null) {
			return false;
		}
		if (task instanceof RecurringTask) {
			return ((RecurringTask) task).willOccur(date);
		}
		return DateUtils.isSameDay(task.getStartDate(), date);
	}
	
	public static boolean isWithinNDays(Task task, Calendar date, int n) {
		if (task.getStartDate() == null) {
			return false;
		}
		int distance = daysBetween(task.getStartDate(), date);
		return ((distance >= 0) && (distance <= n));
	}
	
	public static boolean isWithinPeriod(Task task, Calendar start, Calendar end) {
		Calendar mainDate = task.getStartDate();
		if ((mainDate == null) || (end.before(start))) {
			return false;
		}
		return (isAfterDay(mainDate, start) && isBeforeDay(mainDate, end));
	}
}
