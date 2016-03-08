/** Class Task
 * @author Bao Linh
 * This class stores all the information in a task.
 * Fields:
 *     - ID: the unique ID of the task, determined by the Logic
 *     - description: details about the task
 *     - date: date for the task to be done or deadline
 *     - priority: the task's priority
 */
package logic;

import java.util.Date;

public class Task {
	private CommandType cmd;
	private static int idCounter;
	private String id;
	private String title;
	private String description;
	private Date date;
	private Priority priority;
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setId(String newId) {
		id = newId;
	}
	
	public void setTitle(String newTitle) {
		title = newTitle; 
	}
	
	public void setDescription(String newDes) {
		description = newDes;
	}
	
	public void setDate(Date newDate) {
		date = newDate;
	}
	
	public void setPriority(Priority newPrio) {
		priority = newPrio;
	}
	
	public Task(CommandType cmd,String title, String description, Date date, Priority priority) {
		this.cmd=cmd;
		this.title = title;
		this.description = description;
		this.date = date;
		this.priority = priority;
		idCounter++;
		this.id = new Integer(idCounter).toString();
	}
	
	public Task(CommandType cmd,String title, String description, Date date) {
		this(cmd,title, description, date, Priority.NULL);
	}
	
	public Task(CommandType cmd,String title, Date date) {
		this(cmd,title, null, date, Priority.NULL);
	}
	
	public Task(CommandType cmd,String title) {
		this(cmd,title, null, null, Priority.NULL);
	}
}
