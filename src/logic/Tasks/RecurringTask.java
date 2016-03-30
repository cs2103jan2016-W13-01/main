/* @@author A0112184R
 * This class contains details for recurring tasks
 */
package logic.Tasks;

import java.util.Date;

public class RecurringTask extends GenericTask {
	
	private static final int EVERY_DAY = 1;
	private Date startDate;
	private int period;
	
	public RecurringTask(String title, Date start, int time) {
		super(title);
		startDate = start;
		period = time;
	}
	
	public RecurringTask(String title, Date start) {
		this(title, start, EVERY_DAY);
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
	
	public int getPeriod() {
		return period;
	}
	
	public void setStartDate(Date newDate) {
		startDate = newDate;
	}
	
	public void setPeriod(int newPeriod) {
		period = newPeriod;
	}
}
