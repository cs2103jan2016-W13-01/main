package Storage;

import java.util.*;

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
	public static boolean addNewTask(Task newTask) {
/*		try {
			BufferedWriter addBufferedWriter = initBufferedWriter(storageFile);

			addBufferedWriter.write(newTask.trim());
			addBufferedWriter.newLine();
			addBufferedWriter.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}*/
		
	
		taskList.add(newTask);
		saveTaskList();
		return true;
	}

	// deletes a line from the file based on line number
	public static boolean deleteTask(int taskNumberToDelete) {
	/*	try {
			File tempStorageFile = new File("tempStorageFile.txt");
			BufferedReader deleteBufferedReader = initBufferedReader(storageFile);
			BufferedWriter deleteBufferedWriter = initBufferedWriter(tempStorageFile);

			String line = "";
			int currentTaskNumber = 1;
			while ((line = deleteBufferedReader.readLine()) != null) {
				if (currentTaskNumber != taskNumberToDelete) {		// write all lines except for the
					deleteBufferedWriter.write(line);		// line to be deleted in a temp file
					deleteBufferedWriter.newLine();
					currentTaskNumber++;
				}
				else {
					currentTaskNumber++;						// skip the line to be deleted
				}
			}
			deleteBufferedReader.close();
			deleteBufferedWriter.close();
			storageFile.delete();
			tempStorageFile.renameTo(storageFile);
			return true;
		}
		catch (IOException e) {
			return false;
		}*/
		
		if (taskList.isEmpty() || taskNumberToDelete == 0 || taskNumberToDelete >= taskList.size() - 1) {
			return false;
		}
		else {
			taskList.remove(taskNumberToDelete - 1);
			saveTaskList();
			return true;
		}
	}

	// displays every line in the file in a numbered sequence
	/*
	public static void displayAllTasks() {	
		int linesWritten = 0;
		Controller2.clearDW();
		try {	
			BufferedReader displayBufferedReader = initBufferedReader(storageFile);
			String line = "";
			while ((line = displayBufferedReader.readLine()) != null) {
				linesWritten++;
				Controller2.displayDW(linesWritten+ ". " +line);	
			}
			displayBufferedReader.close();
		}
		catch (IOException e) {
			showToUser(MESSAGE_DISPLAY_ERROR);
		}
	} */

	// deletes all text in the file
	public static boolean clearAllTasks() {
		try {
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
	
	/*
	public static void searchTask(String keyword) {
		try {
			BufferedReader searchBufferedReader = initBufferedReader(storageFile);
			String line = "";
			int matchCount = 0;

			while ((line = searchBufferedReader.readLine()) != null) {
				String[] wordsArray = line.split("\\s+");
				for (String word : wordsArray) {
					if ((word.trim()).equals(keyword)) {
						showToUser(line);
						matchCount++;
						break;
					}
				}
			}
			if (matchCount == 0) {
				showToUser(MESSAGE_NO_MATCH);
			}
		}
		catch (IOException e) {
			showToUser(MESSAGE_SEARCH_ERROR);
		}
	} */
	
	public static ArrayList<String> searchTask(String keyword) {
		
		ArrayList<String> searchResult = new ArrayList<String>();
		try {
			BufferedReader searchBufferedReader = initBufferedReader(storageFile);
			String line = "";

			while ((line = searchBufferedReader.readLine()) != null) {
				String[] wordsArray = line.split("\\s+");
				for (String word : wordsArray) {
					if ((word.trim()).equals(keyword.trim())) {
						searchResult.add(line);
						break;
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
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
	
	private static ArrayList<Task> loadTaskList() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(storageFile));     
			if (br.readLine() == null) {
				taskList = new ArrayList<Task>();
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
		return null;
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