package logic.tasks;

import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;

/* @@author A0112184R
 * This class contains details for recurring tasks
 */
public class RecurringTask extends Task {
	
	private static final int EVERY_DAY = 1;
	private static final int EVERY_YEAR = -2;
	private static final int EVERY_MONTH = -1;
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
	
	public void setStartDate(Calendar newDate) {
		startDate = newDate;
	}
	
	public void setPeriod(int newPeriod) {
		period = newPeriod;
	}
	
	/* @@author A0112184R-reused
	 * Source: http://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-in-java
	 */
	public static int daysBetween(Calendar day1, Calendar day2){
	    Calendar dayOne = (Calendar) day1.clone(),
	            dayTwo = (Calendar) day2.clone();

	    if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
	        return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
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
	
	private int daysUntil(Calendar date) {
		return daysBetween(startDate, date);
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
