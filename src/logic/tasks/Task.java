package logic.tasks;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/* @@author A0112184R
 * This class encloses the general task types
 */
public class Task implements Cloneable {
	
	protected String title;
	protected boolean done;
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	protected boolean isRecurrence;
	protected RecurringTask parent;
	
	public TaskType getType() {
		return TaskType.FLOAT;
	}
	
	public Calendar getMainDate() {
		return null;
	}
	
	public Task(String title) {
		this.title = title;
		done = false;
		isRecurrence = false;
		parent = null;
	}
	
	public String getTitle() {
		if (title == null) return "unspecified title";
		return title;
	}
	
	public String setTitle(String newTitle) {
		title = newTitle;
		return newTitle;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public boolean markDone() {
		done = true;
		return done;
	}
	
	public boolean unmark() {
		done = false;
		return done;
	}
	
	public boolean isRecurrence() {
		return isRecurrence;
	}
	
	public void setRecurrence(boolean recur) {
		isRecurrence = recur;
	}
	
	public RecurringTask getParent() {
		return parent;
	}
	
	public void setParent(RecurringTask task) {
		parent = task;
	}
	
	@Override
	public String toString() {
		return getTitle();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			return (((Task) obj).getType() == this.getType())
					&& ((Task) obj).getTitle().equalsIgnoreCase(this.getTitle());
		}
		return false;
	}
	
	protected static String getDateString(Calendar date) {
		if (date == null) {
			return "null";
		} else {
			return DATE_FORMAT.format(date.getTime());
		}
	}

	public void setDone(boolean isDone) {
		done = isDone;
	}
}
