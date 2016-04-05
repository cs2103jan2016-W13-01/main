package logic.tasks;

import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;

/* @@author A0112184R
 * This class contains details for recurring tasks
 */
public class RecurringTask extends Task {
	
	private static final String DAILY = "every day";
	private static final String WEEKLY = "every week";
	private static final String MONTHLY = "every month";
	private static final String YEARLY = "every year";
	private static final String PERIODLY = "every %s day";
	private static final int EVERY_DAY = 1;
	private static final int EVERY_YEAR = -2;
	private static final int EVERY_MONTH = -1;
	private static final int EVERY_WEEK = 7;
	private Calendar startDate;
	private Calendar endDate;
	private int period;
	
	@Override
	public TaskType getType() {
		return TaskType.RECUR;
	}
	
	@Override
	public Calendar getMainDate() {
		return null;
	}
	
	public RecurringTask(String title, Calendar start, Calendar end, int time) {
		super(title);
		startDate = start;
		endDate = end;
		period = time;
	}
	
	public RecurringTask(String title, Calendar start, Calendar end) {
		this(title, start, end, EVERY_DAY);
	}
	
	public RecurringTask(String title, Calendar start, int time) {
		this(title, start, null, time);
	}
	
	public RecurringTask(String title, int time) {
		this(title, Calendar.getInstance(), time);
	}
	
	public RecurringTask(String title) {
		this(title, Calendar.getInstance(), EVERY_DAY);
	}
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public int getPeriod() {
		return period;
	}
	
	public String getPeriodString() {
		if (period == EVERY_YEAR) {
			return YEARLY;
		} else if (period == EVERY_MONTH) {
			return MONTHLY;
		} else if (period == EVERY_WEEK) {
			return WEEKLY;
		} else if (period == EVERY_DAY) {
			return DAILY;
		} else {
			return String.format(PERIODLY, period);
		}
	}
	
	public void setStartDate(Calendar newDate) {
		startDate = newDate;
	}
	
	public void setPeriod(int newPeriod) {
		period = newPeriod;
	}
	
	private int daysUntil(Calendar date) {
		return TaskUtil.daysBetween(startDate, date);
	}
	
	private boolean isSameDayOfMonth(Calendar date) {
		return startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
	}
	
	private boolean isSameDayOfYear(Calendar date) {
		return (startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH))
				&& (startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH));
	}
	
	private Calendar[] getClosestDate(Calendar[] start, Calendar des, int step) {
		Calendar[] result = new Calendar[2];
		if (des == null || start == null) {
			return null;
		}
		result[0] = Calendar.getInstance();
		result[0].setTime(start[0].getTime());
		if (start[1] != null) {
			result[1] = Calendar.getInstance();
			result[1].setTime(start[1].getTime());
		} else {
			result[1] = null;
		}
		while (DateUtils.truncatedCompareTo(result[0], des, Calendar.DATE) < 0) {
			for (Calendar cal: result) {
				if (cal != null) {
					cal.add(Calendar.DATE, step);
				}
			}
		}
		return result;
	}
	
	public boolean willOccurWithinNDays(Calendar date, int n) {
		Calendar[] closestDates = getClosestDate(new Calendar[]{startDate}, date, period);
		return TaskUtil.daysBetween(closestDates[0], startDate) <= n;
	}
	
	public boolean willOccur(Calendar date) {
		if (date.before(startDate)) {
			return false;
		}
		if (period == EVERY_YEAR) {
			return isSameDayOfYear(date);
		}
		if (period == EVERY_MONTH) {
			return isSameDayOfMonth(date);
		}
		int dayDistance = daysUntil(date);
		return (dayDistance % period) == 0;
	}
	
	public Task generate(Calendar date) {
		Calendar[] start = {startDate, endDate};
		Calendar[] cal = getClosestDate(start, date, period);
		return TaskUtil.getInstance(title, cal[0], cal[1]);
	}
	
	@Override
	public String toString() {
		if (endDate == null) {
			return getTitle() + " " + getDateString(startDate) + "every " + period + " days";
		} else {
			return getTitle() + " " + getDateString(startDate) + " - " + getDateString(endDate) + "every " + period + " days";
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecurringTask) {
			return (((RecurringTask) obj).getType() == this.getType())
					&& ((RecurringTask) obj).getTitle().equalsIgnoreCase(this.getTitle())
					&& ((RecurringTask) obj).getStartDate().equals(this.getStartDate())
					&& ((Session) obj).getEndDate().equals(this.getEndDate())
					&& (((RecurringTask) obj).getPeriod() == this.getPeriod());
		}
		return false;
	}
}
