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
	
	private static final String NULL_TIME = "-";
	private static final String FIELD_SEPARATOR = ";";
	private static final SimpleDateFormat STORAGE_FORMAT = new SimpleDateFormat("HH:mm yyyyMMdd", Locale.ENGLISH);
	private static final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

	public static class TaskComparator implements Comparator<Task> {
		public int compare(Task task1, Task task2) {
			int dateComp;
			Calendar date1 = task1.getStartDate();
			Calendar date2 = task2.getStartDate();
			if (date1 == null && date2 == null) {
				dateComp = task1.getTitle().compareToIgnoreCase(task2.getTitle());
			} else if (date1 == null) {
				dateComp = 1;
			} else if (date2 == null) {
				dateComp = -1;
			} else {
				dateComp = date1.compareTo(date2);
			}
			if (dateComp != 0) {
				return dateComp;
			} else {
				return task1.getTitle().compareToIgnoreCase(task2.getTitle());
			}
		}
	}
	
	public static Task getInstance(String title, Calendar startDate, Calendar endDate, int recurringPeriod) {
		if (startDate == null && endDate == null && recurringPeriod == 0) {
			return new Task(title);
		} else if (recurringPeriod == 0 && endDate == null) {
			return new Deadline(title, startDate);
		} else if (recurringPeriod == 0){
			return new Session(title, startDate, endDate);
		} else {
			return new RecurringTask(title, startDate, endDate, recurringPeriod);
		}
	}
	
	public static Task getInstance(String title, Calendar startDate, Calendar endDate) {
		return getInstance(title, startDate, endDate, 0);
	}
	
	public static Task getInstance(String title, Calendar startDate, int recurringPeriod) {
		return getInstance(title, startDate, null, recurringPeriod);
	}
	
	public static Task getInstance(String title, Calendar startDate) {
		return getInstance(title, startDate, null, 0);
	}
	
	// @@author A0134185H
	public static Task parseFromStorage(String entry) {
		
		String[] parts = entry.split(FIELD_SEPARATOR);
		for (String str: parts) {
			System.out.println(str);
		}
		String titleString = parts[0].split(":", 2)[1].trim();
		
		Calendar startDate;
		Calendar endDate;
		
		try {
			String startDateString = parts[1].split(":", 2)[1].trim();
			startDate = parseDate(startDateString);
		} catch (IndexOutOfBoundsException e) {
			startDate = null;
		}
		
		try {
			String endDateString = parts[2].split(":", 2)[1].trim();
			endDate = parseDate(endDateString);
		} catch (IndexOutOfBoundsException e) {
			endDate = null;
		}
		
		int period;
		try {
			String periodString = parts[3].split(":", 2)[1].trim();
			period = Integer.parseInt(periodString);
		} catch (NumberFormatException e) {
			period = 0;
		} catch (IndexOutOfBoundsException e) {
			period = 0;
		}
		
		boolean isDone;
		try {
			String statusString = parts[4].split(":", 2)[1].trim();
			isDone = statusString.equals("completed");
		} catch (IndexOutOfBoundsException e) {
			isDone = false;
		}
		
		boolean isRecurrence;
		try {
			String isRecurrenceString = parts[5].split(":", 2)[1].trim();
			isRecurrence = isRecurrenceString.equals("true");
		} catch (IndexOutOfBoundsException e) {
			isRecurrence = false;
		}
		Task result = getInstance(titleString, startDate, endDate, period);
		result.setDone(isDone);
		result.setRecurrence(isRecurrence);
		return result;
	}
	
	public static Calendar parseDate(String dateString) {
		if (dateString.equals(NULL_TIME)) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("HH:mm yyyyMMdd", Locale.ENGLISH);
			try {
				cal.setTime(format.parse(dateString));
				return cal;
			} catch (ParseException e) {
				return null;
			}
		}
	}
	
	public static String convertFromDate(Calendar date, DateFormat format) {
		if (date == null) {
			return NULL_TIME;
		} else {
			return format.format(date.getTime());
		}
	}
	
	public static String convertToStorage(Task task) {
		StringBuilder sb = new StringBuilder();
		sb.append("title: " + task.getTitle() + FIELD_SEPARATOR + "\r\n");
		String startDateString, endDateString, statusString, isRecurrenceString;
		startDateString = convertFromDate(task.getStartDate(), STORAGE_FORMAT);
		endDateString = convertFromDate(task.getEndDate(), STORAGE_FORMAT);
		statusString = (task.isDone())? "completed":"incomplete";
		isRecurrenceString = (task.isRecurrence())? "true":"false";
		int period = task.getPeriod();
		sb.append("start: " + startDateString + FIELD_SEPARATOR + "\r\n");
		sb.append("end: " + endDateString + FIELD_SEPARATOR + "\r\n");
		sb.append("recurring period: " + period + FIELD_SEPARATOR + "\r\n");
		sb.append("status: " + statusString + FIELD_SEPARATOR + "\r\n");
		sb.append("is recurrence: " + isRecurrenceString + FIELD_SEPARATOR + "\r\n");
		return sb.toString();
	}
	
	public static String[] toStringArray(Task task) {
		String isDoneString = task.isDone() ? "complete" : "incomplete";
		String titleString = task.getTitle();
		String startString = convertFromDate(task.getStartDate(), DISPLAY_FORMAT);
		String endString;
		if (task instanceof Session) {
			endString = convertFromDate(((Session) task).getEndDate(), DISPLAY_FORMAT);
		} else if (task instanceof RecurringTask) {
			endString = convertFromDate(((RecurringTask) task).getEndDate(), DISPLAY_FORMAT);
		} else {
			endString = NULL_TIME;
		}
		String periodString;
		if (task instanceof RecurringTask) {
			periodString = Integer.toString(((RecurringTask) task).getPeriod());
		} else {
			periodString = NULL_TIME;
		}
		String[] result = {isDoneString, titleString, startString, endString, periodString};
		return result;
	}
	
	
	public static String toString(Task task) {
		String isDoneString = task.isDone() ? "complete" : "incomplete";
		String titleString = task.getTitle();
		String startString = convertFromDate(task.getStartDate(), DISPLAY_FORMAT);
		String endString;
		if (task instanceof Session) {
			endString = convertFromDate(((Session) task).getEndDate(), DISPLAY_FORMAT);
		} else if (task instanceof RecurringTask) {
			endString = convertFromDate(((RecurringTask) task).getEndDate(), DISPLAY_FORMAT);
		} else {
			endString = NULL_TIME;
		}
		String periodString;
		if (task instanceof RecurringTask) {
			periodString = ((RecurringTask) task).getPeriodString();
		} else {
			periodString = NULL_TIME;
		}
		String result = isDoneString + "; " + titleString + "; "
						+ startString + "; " + endString + "; " + periodString;
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
