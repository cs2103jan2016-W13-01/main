package Storage;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.Task;

import java.io.*;

public class Storage implements Serializable {
<<<<<<< HEAD
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
=======

>>>>>>> 674e680e1288bd10261374e65cfc8f5396a00952
	/*
	 * private static final String MESSAGE_ADD_ERROR =
	 * "Error encountered when adding text. Please try again."; private static
	 * final String MESSAGE_DELETE_ERROR =
	 * "Error encountered when deleting task. Please try again"; private static
	 * final String MESSAGE_DISPLAY_ERROR =
	 * "Error encountered when displaying tasks. Please try again"; private
	 * static final String MESSAGE_CLEAR_ERROR =
	 * "Error encountered when clearing all tasks. Please try again"; private
	 * static final String MESSAGE_SORT_ERROR =
	 * "Error encountered when sorting tasks. Please try again."; private static
	 * final String MESSAGE_SEARCH_ERROR =
	 * "Error encountered when searching for keyword. Please try again.";
	 * private static final String MESSAGE_NO_MATCH = "No match found.";
	 */
	private static final String STORAGE_FILE = "storage.txt";
	private static File storageFile;
	private static ArrayList<Task> taskList;
<<<<<<< HEAD
	private static Logger logger = Logger.getLogger(Storage.class.getName()); 
	
	private Storage() {
=======
	private final static Logger LOGGER = Logger.getLogger(Storage.class.getName());

	private Storage() throws IOException, ClassNotFoundException {
>>>>>>> 674e680e1288bd10261374e65cfc8f5396a00952
		retrieveFile();
		taskList = loadTaskList();
	}

	public static File retrieveFile() throws IOException {
		storageFile = new File(STORAGE_FILE);
		if (!storageFile.exists()) {
			// Create file if it does not exist
			storageFile.createNewFile();
		}
		return storageFile;
	}

	// appends a new line of text at the bottom of the file
<<<<<<< HEAD
	public static ArrayList<Task> addNewTask(Task newTask) {
	
		logger.log(Level.INFO, "Adding new Task to the ArrayList");
=======
	public static ArrayList<Task> addNewTask(Task newTask) throws IOException {

		LOGGER.log(Level.INFO, "Adding new Task to the ArrayList");
>>>>>>> 674e680e1288bd10261374e65cfc8f5396a00952
		taskList.add(newTask);
		saveTaskList();
		return taskList;
	}
	
	public static ArrayList<Task> addNewTask(Task newTask, int position) throws IOException {

		LOGGER.log(Level.INFO, "Adding new Task to the ArrayList at position: " + position);
		taskList.add(position, newTask);
		saveTaskList();
		return taskList;
	}

	// deletes a line from the file based on line number
	public static Task deleteTask(int taskNumberToDelete) throws IOException {

		assert (taskNumberToDelete > 0);
		Task deletedTask = null;
		if (!taskList.isEmpty() && taskNumberToDelete < taskList.size()) {
<<<<<<< HEAD
			logger.log(Level.INFO, "Deleting Task from the ArrayList");
			taskList.remove(taskNumberToDelete - 1);
=======
			LOGGER.log(Level.INFO, "Deleting Task from the ArrayList");
			deletedTask = taskList.remove(taskNumberToDelete - 1);
>>>>>>> 674e680e1288bd10261374e65cfc8f5396a00952
			saveTaskList();
		}
		return deletedTask;
	}
	
	public static Task deleteTask(Task taskToDelete) throws IOException {
		
		assert taskToDelete != null: "Attempt to delete a null task";
		if (taskList.contains(taskToDelete)) {
			taskList.remove(taskToDelete);
			saveTaskList();
		} else {
			throw new Error("Task not found in task list");
		}
		return taskToDelete;
	}

	// deletes all text in the file
	public static boolean clearAllTasks() {
		try {
<<<<<<< HEAD
			logger.log(Level.INFO, "Deleting ALL Tasks to the ArrayList");
			storageFile.delete();				// delete the whole file and
			storageFile.createNewFile();		// create a new empty file with the same name
=======
			LOGGER.log(Level.INFO, "Deleting ALL Tasks to the ArrayList");
			storageFile.delete(); // delete the whole file and
			storageFile.createNewFile(); // create a new empty file with the
											// same name
>>>>>>> 674e680e1288bd10261374e65cfc8f5396a00952
			return true;
		} catch (IOException e) {
			return false;
		}
	}

<<<<<<< HEAD
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
		} catch (IOException e) {
			return false;
=======
	public static void sortTasks() throws IOException {
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
>>>>>>> 674e680e1288bd10261374e65cfc8f5396a00952
		}
		sortBufferedReader.close();
		sortBufferedWriter.close();
		storageFile.delete();
		tempStorageFile.renameTo(storageFile);
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

<<<<<<< HEAD
	private static BufferedReader initBufferedReader(File textFile) {
		try {
			FileReader fileReader = new FileReader(textFile.getAbsoluteFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			return bufferedReader;
		} catch (IOException e) {
		}
		return null;
	}

	private static BufferedWriter initBufferedWriter(File textFile) {
		try {	
			FileWriter fileWriter = new FileWriter(textFile.getAbsoluteFile(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			return bufferedWriter;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
=======
	private static BufferedReader initBufferedReader(File textFile) throws FileNotFoundException {
		FileReader fileReader = new FileReader(textFile.getAbsoluteFile());
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		return bufferedReader;
	}

	private static BufferedWriter initBufferedWriter(File textFile) throws IOException {
		FileWriter fileWriter = new FileWriter(textFile.getAbsoluteFile(), true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		return bufferedWriter;
>>>>>>> 674e680e1288bd10261374e65cfc8f5396a00952
	}

	public static ArrayList<Task> loadTaskList() throws ClassNotFoundException, IOException {

		taskList = new ArrayList<Task>();
<<<<<<< HEAD
		try {
			BufferedReader br = new BufferedReader(new FileReader(storageFile));     
			if (br.readLine() == null) {
				br.close();
				return taskList;
			} else {
				try {
					FileInputStream fis = new FileInputStream(storageFile);
					ObjectInputStream ois = new ObjectInputStream(fis);
					taskList = (ArrayList<Task>) ois.readObject();
					ois.close();
					return taskList;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			br.close();
		} catch (IOException e) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
=======
		BufferedReader br = new BufferedReader(new FileReader(storageFile));
		if (br.readLine() == null) {
			br.close();
		} else {
			FileInputStream fis = new FileInputStream(storageFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			taskList = (ArrayList<Task>) ois.readObject();
			ois.close();
		}
		return taskList;
	}

	private static void saveTaskList() throws IOException {
		FileOutputStream fos = new FileOutputStream(storageFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(taskList);
		oos.close();
>>>>>>> 674e680e1288bd10261374e65cfc8f5396a00952
	}
}