package logic.tasks;

/* @@author A0112184R
 * This class encloses the general task types
 */
public abstract class GenericTask implements Comparable<GenericTask> {
	
	protected String title;
	protected boolean done;
	
	public abstract TaskType getType();
	
	public abstract int compareTo(GenericTask task);
	
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
}
