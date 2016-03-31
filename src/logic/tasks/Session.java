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
	
	@Override
	public TaskType getType() {
		return TaskType.SESSION;
	}
	
	@Override
	public Date getMainDate() {
		return start;
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Session) {
			return (((Session) obj).getType() == this.getType())
					&& ((Session) obj).getTitle().equalsIgnoreCase(this.getTitle())
					&& ((Session) obj).getStartDate().equals(this.getStartDate())
					&& ((Session) obj).getEndDate().equals(this.getEndDate());
		}
		return false;
	}
}
