package logic.tasks;

import java.util.Calendar;

import logic.Priority;

/* @@author A0112184R
 * This class stores all the information in a task.
 * Fields:
 *     - title: details about the task
 *     - date: Calendar for the task to be done or deadline
 *     - priority: the task's priority
 */
public class Deadline extends Task {

	private Calendar date;
	private Priority priority;
	
	@Override
	public TaskType getType() {
		return TaskType.NORMAL;
	}
	
	@Override
	public Calendar getMainDate() {
		return date;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setDate(Calendar newDate) {
		date = newDate;
	}
	
	public void setPriority(Priority newPrio) {
		priority = newPrio;
	}
	
	public Deadline(String title, Calendar date, Priority priority) {
		super(title);
		this.date = date;
		this.priority = priority;
	}
	
	public Deadline(String title, Calendar date) {
		this(title, date, Priority.NULL);
	}
	
	public Deadline(String title) {
		this(title, Calendar.getInstance(), Priority.NULL);
	}
	
	@Override
	public String toString() {
		String result = getTitle() + " " + getDateString(date);
		return result;
	}
	
	public boolean equals(Deadline otherTask) {
		return (getTitle().equals(otherTask.getTitle()) && getDate().equals(otherTask.getDate()));
	}
	
	public Deadline clone() {
		Deadline copiedTask = new Deadline(title, date);
		return copiedTask;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Deadline) {
			return (((Deadline) obj).getType() == this.getType())
					&& ((Deadline) obj).getTitle().equalsIgnoreCase(this.getTitle())
					&& ((Deadline) obj).getDate().equals(this.getDate());
		}
		return false;
	}
}
