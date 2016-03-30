/* @@author A0112184R
 * This class stores all the information in a task.
 * Fields:
 *     - title: details about the task
 *     - date: date for the task to be done or deadline
 *     - priority: the task's priority
 */
package logic.tasks;

import java.util.Date;

import logic.Priority;

public class Task extends GenericTask {

	private Date date;
	private Priority priority;
	
	public TaskType getType() {
		return TaskType.NORMAL;
	}
	
	public int compareTo(GenericTask task) {
		if (task instanceof Session) {
			return getDate().compareTo(((Session) task).getStartDate());
		} else if (task instanceof Task) {
			return getDate().compareTo(((Task) task).getDate());
		} else if (task instanceof RecurringTask) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getDateString() {
		if (date == null) {
			return "";
		} else {
			return date.toString();
		}
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setDate(Date newDate) {
		date = newDate;
	}
	
	public void setPriority(Priority newPrio) {
		priority = newPrio;
	}
	
	public Task(String title, Date date, Priority priority) {
		super(title);
		this.date = date;
		this.priority = priority;
	}
	
	public Task(String title, Date date) {
		this(title, date, Priority.NULL);
	}
	
	public Task(String title) {
		this(title, new Date(), Priority.NULL);
	}
	
	public String toString() {
		String result = getTitle() + " " + getDateString();
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
