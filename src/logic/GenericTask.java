package logic;

import java.util.Date;

/* @@author A0112184R
 * This class encloses the general task types
 */
public class GenericTask {
	
	protected String title;
	protected boolean done;
	
	public GenericTask(String title) {
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
	
	public GenericTask getInstance(String title, Date date) {
		if (date == null) {
			return new GenericTask(title);
		} else {
			return new Task(title, date);
		}
	}
}
