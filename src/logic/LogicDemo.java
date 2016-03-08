package Logic;

import java.io.IOException;

/**
 * @author Bao Linh
 * Class Logic
 * The Logic component of DIAMOND project
 * Supported functionalities:
 *    1. start: start the program and choose a storage file
 *    2. addTask: add a task specified by task description and date
 *    3. deleteTask: delete a task specified by task ID in the storage
 *    4. deleteAll: delete all tasks
 *    5. displayTasks: display all tasks onto the UI
 *    6. sortTasks: sort tasks according to various criteria
 *    7. search: search for tasks containing a keyword
 *    8. exit: exit the program
 */

public class LogicDemo {
	
	private static StorageDemo storageEditor;
	
	public static void start(String storageFileName) {
		try {
			storageEditor = new StorageDemo(storageFileName);
		} catch (IOException e) {
			throw new Error(e.toString());
		}
	}
	
	public static String addTask(String taskDes) {
		return storageEditor.add(taskDes);
	}
	
	public static String deleteTask(String taskId) {
		String deletedTaskDes = storageEditor.delete(taskId);
		return deletedTaskDes;
	}
	
	public static String deleteAll() {
		String deleteMessage = storageEditor.clear();
		return deleteMessage;
	}
	
	public static String displayTasks() {
		String taskList = storageEditor.display();
		return taskList;
	}
	
	public static String sortTasks() {
		String sortMessage = storageEditor.sort();
		return sortMessage;
	}
	
	public static String search(String keyword) {
		String searchResults = storageEditor.search(keyword);
		return searchResults;
	}
	
	public static void exit() {
		System.exit(0);
	}
}
