package logic;

import java.util.ArrayList;

/**
 * @author Bao Linh
 * Class TaskProcessor
 * This class contains methods to execute all the user commands and issue commands to other components.
 */
public class TaskProcessor {
	
	private static ArrayList<Task> taskList;
	
	public void add(Task task) {
		taskList.add(task);
	}
	
	public void delete(String taskId) {
		for (Task task : taskList) {
			if (taskId.equals(task.getId())) {
				taskList.remove(task);
				break;
			}
		}
	}
	
	public void initialize() {
		taskList = new ArrayList<Task>();
	}
}
