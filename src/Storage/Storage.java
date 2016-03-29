// @@author: A0134185H

package Storage;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.Task;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Storage implements Serializable {

	private static String STORAGE_FILE = "storage.txt";
	private static File storageFile;
	private static ArrayList<Task> taskList;
	private static ArrayList<Integer> indexList;
	private static Logger logger = Logger.getLogger(Storage.class.getName());

	private Storage() throws IOException, ClassNotFoundException {
		retrieveFile();
		taskList = loadTaskList();
	}
	
	public static ArrayList<Task> getTaskList() {
		return taskList;
	}
	
	public static ArrayList<Integer> getIndexList() {
		return indexList;
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
	public static ArrayList<Integer> addNewTask(Task newTask) throws IOException {

		int taskCounter;
		
		logger.log(Level.INFO, "Adding new Task to the ArrayList");
		assert (taskList != null) : "taskList not initialized";
		assert (newTask != null) : "task is null";
		taskCounter = taskList.size();
		taskList.add(newTask);
		saveTaskList();
		indexList.add(taskCounter);
		
		return indexList;
	}
	// @@author
	
	// @@author A0112184R
	public static ArrayList<Task> addNewTask(Task newTask, int position) throws IOException {

		logger.log(Level.INFO, "Adding new Task to the ArrayList at position: " + position);
		taskList.add(position-1, newTask);
		saveTaskList();
		return taskList;
	}
	// @@author
	
	// @@author: A0134185H
	// deletes a line from the file based on line number
	public static Task deleteTask(int taskNumberToDelete) throws IOException {

		assert (taskNumberToDelete > 0);
		int deleteIndex = taskNumberToDelete - 1;

		if (!taskList.isEmpty() && taskNumberToDelete <= taskList.size()) {
			logger.log(Level.INFO, "Deleting Task from the ArrayList");
			Task deletedTask = taskList.remove(deleteIndex);
			saveTaskList();
			indexList.remove(deleteIndex);
			for (int i = deleteIndex; i < indexList.size(); i++) {
				indexList.set(i, indexList.get(i) - 1);
			}
			return deletedTask;
		}
		return null;
	}
	// @@author
	
	// @@author A0112184R
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
	// @@author

	// @@author: A0134185H
	// deletes all text in the file
	public static boolean clearAllTasks() {
		try {
			logger.log(Level.INFO, "Deleting ALL Tasks to the ArrayList");
			taskList.clear();
			clearStorageFile();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	// @@author

	/* @@author A0112184R
	 * @throws IOException
	 */
	private static void clearStorageFile() throws IOException {
		Files.delete(storageFile.toPath()); // delete the whole file and
		storageFile.createNewFile(); // create a new empty file with the
										// same name
	}
	// @@author
	
	// @@author: A0134185H
	public static void setPath(String pathName) throws IOException{
		STORAGE_FILE = pathName;
		storageFile.renameTo(new File(pathName));
		retrieveFile();
	}
	// @@author
	
	// @@author A0112184R
	public static String getPath() {
		return storageFile.toPath().toString();
	}
	// @@author

	// @@author: A0134185H
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
		}
		sortBufferedReader.close();
		sortBufferedWriter.close();
		storageFile.delete();
		tempStorageFile.renameTo(storageFile);
	}

	public static ArrayList<Integer> searchTask(String keyword) {

		ArrayList<Integer> searchIndexList = new ArrayList<Integer>();
		ArrayList<Task> searchResult = new ArrayList<Task>();
		String lowerCaseKeyword = keyword.toLowerCase();
		for (int i = 0; i < taskList.size(); i++) {
			String taskTitle = taskList.get(i).getTitle();
			if (taskTitle.toLowerCase().contains(lowerCaseKeyword)) {
				logger.log(Level.INFO, "Stores all hits in a separate ArrayList");
				searchResult.add(taskList.get(i));
				searchIndexList.add(indexList.get(i));
			}
		}
		return searchIndexList;
	}

	private static BufferedReader initBufferedReader(File textFile) throws FileNotFoundException {
		FileReader fileReader = new FileReader(textFile.getAbsoluteFile());
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		return bufferedReader;
	}

	private static BufferedWriter initBufferedWriter(File textFile) throws IOException {
		FileWriter fileWriter = new FileWriter(textFile.getAbsoluteFile(), true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		return bufferedWriter;
	}

	public static ArrayList<Task> loadTaskList() throws ClassNotFoundException, IOException {

	/*	taskList = new ArrayList<Task>();
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
		*/
		
		BufferedReader br = initBufferedReader(storageFile);
		taskList = new ArrayList<Task>();
		
		String titleString, dateString;
		while((titleString = br.readLine()) != null && (dateString = br.readLine()) != null) {
			try {
				DateFormat df = new SimpleDateFormat("HH:mm:ss yyyyMMdd", Locale.ENGLISH);
				Date date = df.parse(dateString);
				Task task = new Task(titleString.trim(), date);
				taskList.add(task);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		br.close();
		return taskList;
	}

	private static void saveTaskList() throws IOException {
	/*	FileOutputStream fos = new FileOutputStream(storageFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(taskList);
		oos.close();
	*/
		clearStorageFile();
		BufferedWriter bw = initBufferedWriter(storageFile);
		DateFormat df = new SimpleDateFormat("HH:mm:ss yyyyMMdd");
		for (int i = 0; i < taskList.size(); i++) {
			bw.write(taskList.get(i).getTitle());
			bw.newLine();
			String dateString = df.format(taskList.get(i).getDate());
			bw.write(dateString);
			bw.newLine();
		}
		bw.close();
	}
	// @@author
}
