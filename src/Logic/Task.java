/** Class Task
 * @author Bao Linh
 * This class stores all the information in a task.
 * Fields:
 *     - ID: the unique ID of the task, determined by the Logic
 *     - description: details about the task
 *     - date: date for the task to be done or deadline
 *     - priority: the task's priority
 */
package Logic;

public class Task {
	
	private static int idCounter;
	private String id;
	private String description;
	private String date;
	private Priority priority;
	
	public String getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDate() {
		return date;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setId(String newId) {
		id = newId;
	}
	
	public void setDescription(String newDes) {
		description = newDes;
	}
	
	public void setDate(String newDate) {
		date = newDate;
	}
	
	public void setPriority(Priority newPrio) {
		priority = newPrio;
	}
	
	public Task(String description, String date, Priority priority) {
		this.description = description;
		this.date = date;
		this.priority = priority;
		idCounter++;
		this.id = new Integer(idCounter).toString();
	}
	
	public Task(String description, String date) {
		this(description, date, Priority.NULL);
	}
	
	public Task(String description) {
		this(description, null, Priority.NULL);
	}
}
