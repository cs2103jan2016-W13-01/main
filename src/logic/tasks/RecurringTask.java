package logic.tasks;

import java.util.Calendar;

/* @@author A0112184R
 * This class contains details for recurring tasks
 */
public class RecurringTask extends Task {
	
	private static final int EVERY_DAY = 1;
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
