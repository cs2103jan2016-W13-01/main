/**
 * 
 */
package logic.tasks;

import java.util.Date;

/* @@author A0112184R
 * This class contains details for sessions with start and end time
 */
public class Session extends Task {
	
	private Date start;
	private Date end;
	
	public TaskType getType() {
		return TaskType.SESSION;
	}
	
	public int compareTo(Task task) {
		if (task instanceof Session) {
			return getStartDate().compareTo(((Session) task).getStartDate());
		} else if (task instanceof Deadline) {
			return getStartDate().compareTo(((Deadline) task).getDate());
		} else if (task instanceof RecurringTask) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public Session(String title, Date startDate, Date endDate) {
		super(title);
		start = startDate;
		end = endDate;
	}
	
	public Date getStartDate() {
		return start;
	}
	
	public Date getEndDate() {
		return end;
	}
	
	public void setStartDate(Date newStart) {
		start = newStart;
	}
	
	public void setEndDate(Date newEnd) {
		end = newEnd;
	}
}
