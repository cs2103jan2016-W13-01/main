package logic.tasks;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;

/* @@author A0112184R
 * This class encloses the general task types
 */
public class Task implements Cloneable, Comparable<Task> {
	
	private static final String FIELD_SEPARATOR = "; ";
	private static final String NULL_DATE = "-";
	private static final String STATUS_UNDONE = "incomplete";
	private static final String STATUS_DONE = "completed";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd MMM");
	private String title;
	private boolean done;
	private Calendar start;
	private Calendar end;

	public Calendar getStartDate() {
		return start;
	}
	
	public Calendar getEndDate() {
		return end;
	}
	
	public Task(String title, Calendar startDate, Calendar endDate) {
		this.title = title;
		start = startDate;
		end = endDate;
		done = false;
	}
	
	public Calendar getMainDate() {
		if (start != null) {
			return start;
		} else {
			return end;
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public String setTitle(String newTitle) {
		title = newTitle;
		return newTitle;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public void setDone(boolean value) {
		done = value;
	}
	
	public int getPeriod() {
		return 0;
	}
	
	@Override
	public String toString() {
		String titleString, startString, endString, isDoneString;
		titleString = (title  == null)? "unspecified": title;
		startString = (start == null)? NULL_DATE: DATE_FORMAT.format(start);
		endString = (end == null)? NULL_DATE: DATE_FORMAT.format(end);
		isDoneString = (done == true)? STATUS_DONE: STATUS_UNDONE;
		return isDoneString + FIELD_SEPARATOR + titleString + FIELD_SEPARATOR
				+ startString + FIELD_SEPARATOR + endString;
	}
	
	public boolean willOccur(Calendar date) {
		return (DateUtils.truncatedCompareTo(start, date, Calendar.DATE) <= 0)
				&& (DateUtils.truncatedCompareTo(end, date, Calendar.DATE) >= 0);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			return equalTitles(getTitle(), ((Task) obj).getTitle())
					&& equalDates(getStartDate(), ((Task) obj).getStartDate())
					&& equalDates(getEndDate(), ((Task) obj).getEndDate())
					&& (isDone() == ((Task) obj).isDone());
		}
		return false;
	}
	
	private static boolean equalDates(Calendar date1, Calendar date2) {
		if (date1 == null) {
			return date2 == null;
		} else {
			return DateUtils.truncatedEquals(date1, date2, Calendar.MINUTE);
		}
	}
	
	private static boolean equalTitles(String title1, String title2) {
		if (title1 == null) {
			return title2 == null;
		} else {
			return title1.trim().equalsIgnoreCase(title2.trim());
		}
	}
	
	@Override
	public int compareTo(Task task) {
		Calendar date = task.getMainDate();
		Calendar thisDate = getMainDate();
		int dateComp = compareDate(thisDate, date);
		if (dateComp != 0) {
			return dateComp;
		} else {
			return compareTitle(title, task.getTitle());
		}
	}
	
	private int compareTitle(String title1, String title2) {
		if (title1 == null && title2 == null) {
			return 0;
		} else if (title1 == null) {
			return -1;
		} else if (title2 == null) {
			return 1;
		} else {
			return title1.trim().compareToIgnoreCase(title2.trim());
		}
	}

	private int compareDate(Calendar date1, Calendar date2) {
		if (date1 == null && date2 == null) {
			return 0;
		} else if (date1 == null) {
			return -1;
		} else if (date2 == null) {
			return 1;
		} else {
			return DateUtils.truncatedCompareTo(date1, date2, Calendar.MINUTE);
		}
	}
}
