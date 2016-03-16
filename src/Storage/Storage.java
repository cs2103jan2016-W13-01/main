package Storage;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.Task;

import java.io.*;

public class Storage implements Serializable {
	
	/*
	private static final String MESSAGE_ADD_ERROR = "Error encountered when adding text. Please try again.";
	private static final String MESSAGE_DELETE_ERROR = "Error encountered when deleting task. Please try again";
	private static final String MESSAGE_DISPLAY_ERROR = "Error encountered when displaying tasks. Please try again";
	private static final String MESSAGE_CLEAR_ERROR = "Error encountered when clearing all tasks. Please try again";
	private static final String MESSAGE_SORT_ERROR = "Error encountered when sorting tasks. Please try again.";
	private static final String MESSAGE_SEARCH_ERROR = "Error encountered when searching for keyword. Please try again.";
	private static final String MESSAGE_NO_MATCH = "No match found.";
	*/
	private static final String STORAGE_FILE = "storage.txt";
	private static File storageFile;
	private static ArrayList<Task> taskList;
	private final static Logger LOGGER = Logger.getLogger(Storage.class.getName()); 
	
	private Storage() {
		retrieveFile();
		taskList = loadTaskList();
	}

	public static File retrieveFile() {
		storageFile = new File(STORAGE_FILE);
		if (!storageFile.exists()) {
			// Create file if it does not exist
			try {
				storageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return storageFile;
	}

	// appends a new line of text at the bottom of the file
	public static ArrayList<Task> addNewTask(Task newTask) {
	
		LOGGER.log(Level.INFO, "Adding new Task to the ArrayList");
		taskList.add(newTask);
		saveTaskList();
		return taskList;
	}

	// deletes a line from the file based on line number
	public static ArrayList<Task> deleteTask(int taskNumberToDelete) {
		
		assert(taskNumberToDelete > 0);
		if (!taskList.isEmpty() && taskNumberToDelete < taskList.size()) {
			LOGGER.log(Level.INFO, "Deleting Task from the ArrayList");
			taskList.remove(taskNumberToDelete - 1);
			saveTaskList();
		}
		return taskList;
	}

	// deletes all text in the file
	public static boolean clearAllTasks() {
		try {
			LOGGER.log(Level.INFO, "Deleting ALL Tasks to the ArrayList");
			storageFile.delete();				// delete the whole file and
			storageFile.createNewFile();		// create a new empty file with the same name
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}

	public static boolean sortTasks() {
		try {
			File tempStorageFile = new File("tempStorageFile.txt");
			BufferedReader sortBufferedReader = initBufferedReader(storageFile);
			BufferedWriter sortBufferedWriter = initBufferedWriter(tempStorageFile);
			String line;
			ArrayList<String> textList = new ArrayList<String>();
			while ((line = sortBufferedReader.readLine()) != null) {
				textList.add(line);
			}
			Collections.sort(textList, String.CASE_INSENSITIVE_ORDER);
			for (String output : textList) {
				sortBufferedWriter.write(output);
				sortBufferedWriter.newLine();
			}
			sortBufferedReader.close();
			sortBufferedWriter.close();
			storageFile.delete();
			tempStorageFile.renameTo(storageFile);
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}
	
	public static ArrayList<Task> searchTask(String keyword) {
		
		ArrayList<Task> searchResult = new ArrayList<Task>();
		String lowerCaseKeyword = keyword.toLowerCase();
		for (int i = 0; i < taskList.size(); i++) {
			String taskTitle = taskList.get(i).getTitle();
			if (taskTitle.toLowerCase().contains(lowerCaseKeyword)) {
				LOGGER.log(Level.INFO, "Stores all hits in a separate ArrayList");
				searchResult.add(taskList.get(i));
			}
		}
		return searchResult;
	}

	private static BufferedReader initBufferedReader(File textFile) {
		try {
			FileReader fileReader = new FileReader(textFile.getAbsoluteFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			return bufferedReader;
		}
		catch (IOException e) {
		}
		return null;
	}

	private static BufferedWriter initBufferedWriter(File textFile) {
		try {	
			FileWriter fileWriter = new FileWriter(textFile.getAbsoluteFile(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			return bufferedWriter;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Task> loadTaskList() {
		
		taskList = new ArrayList<Task>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(storageFile));     
			if (br.readLine() == null) {
				br.close();
				return taskList;
			}
			else {
				try {
					FileInputStream fis = new FileInputStream(storageFile);
					ObjectInputStream ois = new ObjectInputStream(fis);
					taskList = (ArrayList<Task>) ois.readObject();
					ois.close();
					return taskList;
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return taskList;
	}
	
	private static boolean saveTaskList() {
		try {
			FileOutputStream fos = new FileOutputStream(storageFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(taskList);
			oos.close();
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}