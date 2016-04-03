package logic.tasks;

import java.util.Calendar;

/* @@author A0112184R
 * This class contains details for sessions with start and end time
 */
public class Session extends Task {
	
	private Calendar start;
	private Calendar end;
	
	@Override
	public TaskType getType() {
		return TaskType.SESSION;
	}
	
	@Override
	public Calendar getMainDate() {
		return start;
	}
	
	public Session(String title, Calendar startDate, Calendar endDate) {
		super(title);
		start = startDate;
		end = endDate;
	}
	
	public Calendar getStartDate() {
		return start;
	}
	
	public Calendar getEndDate() {
		return end;
	}
	
	public void setStartDate(Calendar newStart) {
		start = newStart;
	}
	
	public void setEndDate(Calendar newEnd) {
		end = newEnd;
	}
	
	@Override
	public String toString() {
		return getTitle() + " " + getDateString(start) + " - " + getDateString(end);
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
