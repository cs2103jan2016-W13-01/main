package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import logic.tasks.*;

/* @@author A0112184R
 * This class is responsible for reading and writing from the storage file
 */
public class Database {
	
	private static final String CONFIG_FILE_NAME = "./src/document/config.txt";
	private static final String FLOAT_LIST_FILE_NAME = "%s/data/floatList.txt";
	private static final String DEADLINE_LIST_FILE_NAME = "%s/data/deadlineList.txt";
	private static final String SESSION_LIST_FILE_NAME = "%s/data/sessionList.txt";
	private static final String RECURRING_LIST_FILE_NAME = "%s/data/recurringFileName.txt";
	
	
	private static File configFile;
	
	private static String storagePath;
	
	private static File floatListFile;
	private static File deadlineListFile;
	private static File sessionListFile;
	private static File recurringListFile;
	
	public static void retrieveFiles() throws IOException {
		String directoryName = getStorageDirectory();
		floatListFile = initFile(String.format(directoryName, FLOAT_LIST_FILE_NAME));
		deadlineListFile = initFile(String.format(directoryName, DEADLINE_LIST_FILE_NAME));
		sessionListFile = initFile(String.format(directoryName, SESSION_LIST_FILE_NAME));
		recurringListFile = initFile(String.format(directoryName, RECURRING_LIST_FILE_NAME));
	}
	
	private static File initFile(String filename) throws IOException {
		File result = new File(filename);
		if (!result.exists()) {
			result.createNewFile();
		}
		return result;
	}

	private static String getStorageDirectory() throws FileNotFoundException {
		Scanner sc = new Scanner(configFile);
		storagePath = sc.nextLine().trim();
		sc.close();
		return storagePath;
	}
	
	public static void initialize() throws IOException {
		configFile = initFile(CONFIG_FILE_NAME);
		retrieveFiles();
		load();
	}

	public static void setPath(String pathName) throws IOException{
		configFile.delete();
		configFile.createNewFile();
		BufferedWriter bf = initBufferedWriter(configFile);
		bf.write(pathName);
		bf.newLine();
		bf.close();
		retrieveFiles();
		save();
	}
	
	public static String getPath() {
		return storagePath;
	}
	
	public static void clear() throws IOException {
		clearFile(floatListFile);
		clearFile(deadlineListFile);
		clearFile(sessionListFile);
		clearFile(recurringListFile);
	}
	
	public static void clearFile(File file) throws IOException {
		Files.delete(file.toPath()); // delete the whole file and
		file.createNewFile(); // create a new empty file with the same name
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
	
	public static void save() throws IOException {
		saveFloat();
		saveDeadline();
		saveSession();
		saveRecurring();
	}
	
	public static void saveFloat() throws IOException {
		save(GrandTaskList.getFloatList(), floatListFile);
	}
	
	public static void saveDeadline() throws IOException {
		save(GrandTaskList.getDeadlineList(), deadlineListFile);
	}
	
	public static void saveSession() throws IOException {
		save(GrandTaskList.getSessionList(), sessionListFile);
	}
	
	public static void saveRecurring() throws IOException {
		save(GrandTaskList.getRecurringList(), recurringListFile);
	}
	
	public static void load() throws IOException {
		loadFloat();
		loadDeadline();
		loadSession();
		loadRecurring();
	}
	
	public static void loadFloat() throws IOException {
		load(GrandTaskList.getFloatList(), floatListFile);
	}
	
	public static void loadDeadline() throws IOException {
		load(GrandTaskList.getDeadlineList(), deadlineListFile);
	}
	
	public static void loadSession() throws IOException {
		load(GrandTaskList.getSessionList(), sessionListFile);
	}
	
	public static void loadRecurring() throws IOException {
		load(GrandTaskList.getRecurringList(), recurringListFile);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Task> void load(TaskList<T> list, File file) throws IOException {
		
		BufferedReader br = initBufferedReader(file);
		String begin;
		while ((begin = br.readLine()) != null) {
			if (begin.equals("{")) {
				StringBuilder entryBuilder = new StringBuilder();
				String end;
				while (!(end = br.readLine()).equals("}")) {
					entryBuilder.append(end);
				}
				String entry = entryBuilder.toString();
				T task = (T) TaskUtil.parseFromStorage(entry);
				list.add(task);
			}
		}
		br.close();
	}
	
	public static <T extends Task> void save(TaskList<T> list, File file) throws IOException {
		BufferedWriter bw = initBufferedWriter(file);
		for (T task: list) {
			bw.write("{\r\n");
			bw.write(TaskUtil.convertToStorage(task));
			bw.write("\r\n}");
		}
		bw.close();
	}
}
