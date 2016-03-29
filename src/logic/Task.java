/* @@author A0112184R
 * This class stores all the information in a task.
 * Fields:
 *     - title: details about the task
 *     - date: date for the task to be done or deadline
 *     - priority: the task's priority
 */
package logic;

import java.util.Date;

public class Task {

	private String title;
	private Date date;
	private Priority priority;
	
	public String getTitle() {
		if (title == null) {
			return "unspecifed title";
		}
		return title;
	}
	
	public Date getDate() {
		if (date == null) {
			return new Date();
		}
		return date;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setTitle(String newTitle) {
		title = newTitle; 
	}
	
	public void setDate(Date newDate) {
		date = newDate;
	}
	
	public void setPriority(Priority newPrio) {
		priority = newPrio;
	}
	
	public Task(String title, String description, Date date, Priority priority) {
		this.title = title;
		this.date = date;
		this.priority = priority;
	}
	
	public Task(String title, String description, Date date) {
		this(title, description, date, Priority.NULL);
	}
	
	public Task(String title, Date date) {
		this(title, "null", date, Priority.NULL);
	}
	
	public Task(String title) {
		this(title, "null", new Date(), Priority.NULL);
	}
	
	public String toString() {
		String dateString;
		String titleString;
		if (date != null) {
			dateString = date.toString();
		} else {
			dateString = "unspecified date";
		}
		if (title != null) {
			titleString = title;
		} else {
			titleString = "unspecified title";
		}
		String result = titleString + " " + dateString;
		return result;
	}
	
	public boolean equals(Task otherTask) {
		return (getTitle().equals(otherTask.getTitle()) && getDate().equals(otherTask.getDate()));
	}
	
	public Task clone() {
		Task copiedTask = new Task(title, date);
		return copiedTask;
	}
}
