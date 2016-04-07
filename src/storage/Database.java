package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

import logic.tasks.*;

/* @@author A0112184R
 * This class is responsible for reading and writing from the storage file
 */
public class Database {
	
	private static final String DEFAULT_STORAGE_DIR = ".";
	private static final String CONFIG_FILE_NAME = "./src/document/config.txt";
	private static final String DIRECTORY_NAME = "%s/data";
	private static final String NORMAL_LIST_FILE_NAME = "%s/data/normalList.txt";
	private static final String RECURRING_LIST_FILE_NAME = "%s/data/recurringList.txt";
	private static final String DONE_LIST_FILE_NAME = "%s/data/doneList.txt";
	
	private static File configFile;
	
	private static String storagePath;
	
	private static File normalListFile;
	private static File recurringListFile;
	private static File doneListFile;
	
	public static void retrieveFiles() throws IOException {
		storagePath = getStorageDirectory();
		File data = new File(String.format(DIRECTORY_NAME, storagePath));
		if (!data.exists()) {
			StorageLogger.log(Level.INFO, "Creating storage folder");
			if (data.mkdirs()) {
				StorageLogger.log(Level.INFO, "Storage folder created");
			} else {
				StorageLogger.log(Level.SEVERE, "Storage folder not created");
				throw new IOException("Storage folder not created");
			}
		}
		normalListFile = initFile(String.format(NORMAL_LIST_FILE_NAME, storagePath));
		recurringListFile = initFile(String.format(RECURRING_LIST_FILE_NAME, storagePath));
		doneListFile = initFile(String.format(DONE_LIST_FILE_NAME, storagePath));
	}
	
	static File initFile(String filename) throws IOException {
		StorageLogger.log(Level.INFO, "Initializing file: " + filename);
		File result = new File(filename);
		if (!result.exists()) {
			result.createNewFile();
		}
		return result;
	}

	private static String getStorageDirectory() throws IOException {
		Scanner sc = new Scanner(configFile);
		if (sc.hasNextLine()) {
			storagePath = sc.nextLine().trim();
		} else {
			BufferedWriter bw = initBufferedWriter(configFile);
			bw.write(DEFAULT_STORAGE_DIR);
			bw.newLine();
			bw.close();
			storagePath = DEFAULT_STORAGE_DIR;
		}
		sc.close();
		return storagePath;
	}
	
	public static void initialize() throws IOException {
		StorageLogger.initialize();
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
		clearFile(normalListFile);
		clearFile(recurringListFile);
		clearFile(doneListFile);
	}
	
	public static void clearFile(File file) throws IOException {
		file.delete(); // delete the whole file and
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
		saveNormal();
		saveRecurring();
		saveDone();
	}
	
	public static void saveNormal() throws IOException {
		save(GrandTaskList.getNormalList(), normalListFile);
	}
	
	public static void saveRecurring() throws IOException {
		save(GrandTaskList.getRecurringList(), recurringListFile);
	}
	
	public static void saveDone() throws IOException {
		save(GrandTaskList.getDoneList(), doneListFile);
	}
	
	public static void load() throws IOException {
		loadNormal();
		loadRecurring();
		loadDone();
	}
	
	public static void loadNormal() throws IOException {
		load(GrandTaskList.getNormalList(), normalListFile);
	}
	
	public static void loadRecurring() throws IOException {
		load(GrandTaskList.getRecurringList(), recurringListFile);
	}
	
	public static void loadDone() throws IOException {
		load(GrandTaskList.getDoneList(), doneListFile);
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
		clearFile(file);
		BufferedWriter bw = initBufferedWriter(file);
		for (T task: list) {
			bw.write("{\r\n");
			bw.write(TaskUtil.convertToStorage(task));
			bw.write("}\r\n");
		}
		bw.close();
	}
}
