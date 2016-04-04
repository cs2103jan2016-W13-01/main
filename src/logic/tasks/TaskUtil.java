package logic.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

/* @@author A0112184R
 * This class contains the getInstance() method to generate a Task object
 * and the comparator for tasks
 */
public class TaskUtil {
	
	private static final String FIELD_SEPARATOR = ";";

	public static class TaskComparator implements Comparator<Task> {
		public int compare(Task task1, Task task2) {
			Calendar date1 = task1.getMainDate();
			Calendar date2 = task2.getMainDate();
			if (date1 == null && date2 == null) {
				return task1.getTitle().compareToIgnoreCase(task2.getTitle());
			} else if (date1 == null) {
				return 1;
			} else if (date2 == null) {
				return -1;
			} else {
				return date1.compareTo(date2);
			}
		}
	}
	
	public static Task getInstance(String title, Calendar startDate, Calendar endDate, int recurringPeriod) {
		if (startDate == null && endDate == null) {
			return new Task(title);
		} else if (recurringPeriod > 0) {
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
}
