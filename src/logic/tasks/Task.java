package logic.tasks;

import java.util.Date;

/* @@author A0112184R
 * This class encloses the general task types
 */
public class Task implements Cloneable {
	
	protected String title;
	protected boolean done;
	
	public TaskType getType() {
		return TaskType.FLOAT;
	}
	
	public Date getMainDate() {
		return null;
	}
	
	public Task(String title) {
		this.title = title;
		done = false;
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			return (((Task) obj).getType() == this.getType())
					&& ((Task) obj).getTitle().equalsIgnoreCase(this.getTitle());
		}
		return false;
	}
}
