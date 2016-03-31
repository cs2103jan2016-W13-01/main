/* @@author A0112184R
 * This class contains details for recurring tasks
 */
package logic.tasks;

import java.util.Date;

public class RecurringTask extends Task {
	
	private static final int EVERY_DAY = 1;
	private Date startDate;
	private Date endDate;
	private int period;
	
	@Override
	public TaskType getType() {
		return TaskType.RECUR;
	}
	
	@Override
	public Date getMainDate() {
		return null;
	}
	
	public RecurringTask(String title, Date start, Date end, int time) {
		super(title);
		startDate = start;
		endDate = end;
		period = time;
	}
	
	public RecurringTask(String title, Date start, Date end) {
		this(title, start, end, EVERY_DAY);
	}
	
	public RecurringTask(String title, Date start, int time) {
		this(title, start, null, time);
	}
	
	public RecurringTask(String title, int time) {
		this(title, new Date(), time);
	}
	
	public RecurringTask(String title) {
		this(title, new Date(), EVERY_DAY);
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public int getPeriod() {
		return period;
	}
	
	public void setStartDate(Date newDate) {
		startDate = newDate;
	}
	
	public void setPeriod(int newPeriod) {
		period = newPeriod;
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
