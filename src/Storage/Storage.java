// @@author: A0134185H

package Storage;

import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Storage {

	private static final String CONFIG_FILE_NAME = "./src/document/config.txt";
	private static File configFile;
	
	private static String storageFileName;
	private static File storageFile;
	private static ArrayList<Deadline> taskList;
	private static ArrayList<Integer> indexList;
	private static Logger logger = Logger.getLogger(Storage.class.getName());
	private static int floatBeginOnTaskList;
	private static int floatBeginOnIndexList;
	
	public static Deadline latestDeletedTask;
	public static int latestDeletedIndex;

	private Storage() throws IOException, ClassNotFoundException {
		retrieveFile();
		taskList = loadTaskList();
	}
	
	public static ArrayList<Deadline> getTaskList() {
		return taskList;
	}
	
	public static ArrayList<Integer> getIndexList() {
		return indexList;
	}
	
	public static int getFloatBegin() {
		return floatBeginOnTaskList;
	}
	
	public static int getFloatBeginOnIndexList() {
		return floatBeginOnIndexList;
	}

	public static File retrieveFile() throws IOException {
		
		configFile = new File(CONFIG_FILE_NAME);
		Scanner sc = new Scanner(configFile);
		storageFileName = sc.nextLine();
		sc.close();
		
		storageFile = new File(storageFileName);
		if (!storageFile.exists()) {
			// Create file if it does not exist
			storageFile.createNewFile();
		}
		return storageFile;
	}

	// appends a new line of text at the bottom of the file
	public static ArrayList<Integer> addNewTask(Deadline newTask) throws IOException {

		logger.log(Level.INFO, "Adding new Task to the ArrayList");
		assert (taskList != null) : "taskList not initialized";
		assert (newTask != null) : "task is null";
		if (newTask.getDateString().equals("")) {
			taskList.add(newTask);
		} else {
			taskList.add(floatBeginOnTaskList, newTask);
			floatBeginOnTaskList++;
		}
		saveTaskList();
		//indexList.add(taskList.size());
		displayAllTasks();
		
		return indexList;
	}
	// @@author
	
	// @@author A0112184R
	public static ArrayList<Integer> addNewTask(Deadline newTask, int taskListPosition) throws IOException {

		logger.log(Level.INFO, "Adding new Task to the ArrayList at position: " + taskListPosition);
		taskList.add(taskListPosition, newTask);
		if (newTask.getDate() != null) {
			floatBeginOnTaskList++;
		}
		saveTaskList();
		displayAllTasks();
		return indexList;
	}
	// @@author
	
	// @@author: A0134185H
	// deletes a line from the file based on line number
	public static Deadline deleteTask(int deleteIndex) throws IOException {

		assert (deleteIndex >= 0);

		if (!taskList.isEmpty() && deleteIndex <= taskList.size()) {
			logger.log(Level.INFO, "Deleting Task from the ArrayList");
			latestDeletedTask = taskList.remove(deleteIndex);
			if (deleteIndex < floatBeginOnTaskList) {
				floatBeginOnTaskList--;
			}
			saveTaskList();
			latestDeletedIndex = indexList.indexOf(new Integer(deleteIndex));
			indexList.remove(latestDeletedIndex);
			if (latestDeletedIndex < floatBeginOnIndexList) {
				floatBeginOnIndexList--;
			}
			for (int i = latestDeletedIndex; i < indexList.size(); i++) {
				indexList.set(i, indexList.get(i) - 1);
			}
		}
		return latestDeletedTask;
	}
	// @@author
	
	// @@author A0112184R
	public static Deadline deleteTask(Deadline taskToDelete) throws IOException {
		
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
			logger.log(Level.INFO, "Deleting ALL Tasks from the ArrayList");
			taskList.clear();
			indexList.clear();
			floatBeginOnTaskList = 0;
			floatBeginOnIndexList = 0;
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
		configFile.delete();
		configFile.createNewFile();
		BufferedWriter bf = initBufferedWriter(configFile);
		bf.write(pathName);
		bf.newLine();
		bf.close();
		retrieveFile();
		saveTaskList();
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

	public static ArrayList<Integer> searchTask(Predicate<Deadline> p) {

		indexList.clear();
		int i;
		logger.log(Level.INFO, "Storing all hits indices in the indexList");
		for (i = 0; i < floatBeginOnTaskList; i++) {
			Deadline task = taskList.get(i);
			if (p.test(task)) {
				indexList.add(i);
			}
		}
		floatBeginOnIndexList = indexList.size();
		for (i = floatBeginOnTaskList; i < taskList.size(); i++) {
			Deadline task = taskList.get(i);
			if (p.test(task)) {
				indexList.add(i);
			}
		}
		return indexList;
	}
	
	public static ArrayList<Integer> displayAllTasks() {
		indexList.clear();
		for (int i = 0; i < taskList.size(); i++) {
			indexList.add(i);
		}
		floatBeginOnIndexList = floatBeginOnTaskList;
		return indexList;
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

	public static ArrayList<Deadline> loadTaskList() throws ClassNotFoundException, IOException {

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
		taskList = new ArrayList<Deadline>();
		floatBeginOnTaskList = 0;
			
		String titleString, dateString;
		while((titleString = br.readLine()) != null && (dateString = br.readLine()) != null) {
			try {
				DateFormat df = new SimpleDateFormat("HH:mm:ss yyyyMMdd", Locale.ENGLISH);
				Date date;
				if (dateString.equals("null")) {
					date = null;
				} else {
					date = df.parse(dateString);
					floatBeginOnTaskList++;
				}
				Deadline task = new Deadline(titleString.trim(), date);
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
			Deadline task = taskList.get(i);
			bw.write(task.getTitle());
			bw.newLine();
			Date date = task.getDate();
			String dateString;
			if (date == null) {
				dateString = "null";
			} else {
				dateString = df.format(date);
			}
			bw.write(dateString);
			bw.newLine();
		}
		bw.close();
	}
	
	public static void initialize() throws ClassNotFoundException, IOException {
		retrieveFile();
		loadTaskList();
		initIndexList();
	}
	// @@author

	private static void initIndexList() {
		indexList = new ArrayList<Integer>();
		displayAllTasks();
	}
}
