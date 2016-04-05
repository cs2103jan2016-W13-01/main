package logic.tasks;

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
	
	private static final String FIELD_SEPARATOR = ";";

	public static class TaskComparator implements Comparator<Task> {
		public int compare(Task task1, Task task2) {
			int dateComp;
			Calendar date1 = task1.getMainDate();
			Calendar date2 = task2.getMainDate();
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
		if (startDate == null && endDate == null) {
			return new Task(title);
		} else if (recurringPeriod != 0) {
			return new RecurringTask(title, startDate, endDate, recurringPeriod);
		} else if (endDate == null){
			return new Deadline(title, startDate);
		} else {
			return new Session(title, startDate, endDate);
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
		return getInstance(titleString, startDate, endDate, period);
	}
	
	public static Calendar parseDate(String dateString) {
		if (dateString.equals("null")) {
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
	
	public static String convertFromDate(Calendar date) {
		if (date == null) {
			return "null";
		} else {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm yyyyMMdd", Locale.ENGLISH);
			return format.format(date.getTime());
		}
	}
	
	public static String convertToStorage(Task task) {
		StringBuilder sb = new StringBuilder();
		sb.append("title: " + task.getTitle() + FIELD_SEPARATOR + "\r\n");
		String startDateString, endDateString;
		int period;
		if (task instanceof RecurringTask) {
			period = ((RecurringTask) task).getPeriod();
			endDateString = convertFromDate(((RecurringTask) task).getEndDate());
			startDateString = convertFromDate(((RecurringTask) task).getStartDate());
		} else {
			period = 0;
			startDateString = convertFromDate(task.getMainDate());
		}
		if (task instanceof Session) {
			endDateString = convertFromDate(((Session) task).getEndDate());
		} else {
			endDateString = "null";
		}
		sb.append("start: " + startDateString + FIELD_SEPARATOR + "\r\n");
		sb.append("end: " + endDateString + FIELD_SEPARATOR + "\r\n");
		sb.append("recurring period: " + period + FIELD_SEPARATOR + "\r\n");
		return sb.toString();
	}
	
	public static String[] toStringArray(Task task) {
		String isDoneString = task.isDone() ? "complete" : "incomplete";
		String titleString = task.getTitle();
		String startString = convertFromDate(task.getMainDate());
		String endString;
		if (task instanceof Session) {
			endString = convertFromDate(((Session) task).getEndDate());
		} else if (task instanceof RecurringTask) {
			endString = convertFromDate(((RecurringTask) task).getEndDate());
		} else {
			endString = "null";
		}
		String periodString;
		if (task instanceof RecurringTask) {
			periodString = Integer.toString(((RecurringTask) task).getPeriod());
		} else {
			periodString = "null";
		}
		String[] result = {isDoneString, titleString, startString, endString, periodString};
		return result;
	}
	
	
	public static String toString(Task task) {
		String isDoneString = task.isDone() ? "complete" : "incomplete";
		String titleString = task.getTitle();
		String startString = convertFromDate(task.getMainDate());
		String endString;
		if (task instanceof Session) {
			endString = convertFromDate(((Session) task).getEndDate());
		} else if (task instanceof RecurringTask) {
			endString = convertFromDate(((RecurringTask) task).getEndDate());
		} else {
			endString = "null";
		}
		String periodString;
		if (task instanceof RecurringTask) {
			periodString = ((RecurringTask) task).getPeriodString();
		} else {
			periodString = "null";
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
		if (task.getMainDate() == null) {
			return false;
		}
		if (task instanceof RecurringTask) {
			return ((RecurringTask) task).willOccur(date);
		}
		return DateUtils.isSameDay(task.getMainDate(), date);
	}
	
	public static boolean isWithinNDays(Task task, Calendar date, int n) {
		if (task.getMainDate() == null) {
			return false;
		}
		int distance = daysBetween(task.getMainDate(), date);
		return ((distance >= 0) && (distance <= n));
	}
	
	public static boolean isWithinPeriod(Task task, Calendar start, Calendar end) {
		Calendar mainDate = task.getMainDate();
		if ((mainDate == null) || (end.before(start))) {
			return false;
		}
		return (isAfterDay(mainDate, start) && isBeforeDay(mainDate, end));
	}
}
