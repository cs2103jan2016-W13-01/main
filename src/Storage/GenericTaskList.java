/* author A0112184R
 * 
 */
package Storage;

import java.util.ArrayList;

import logic.tasks.Task;

/* @@author A0112184R
 * This class contains the lists of specific types of tasks
 */
public class GenericTaskList<T extends Task> {
	
	ArrayList<T> taskList;
	
	public void add(T task) {
		
	}
}
