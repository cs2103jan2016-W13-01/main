import java.util.*;
import java.io.*;

public class Diamond {

	private static final String MESSAGE_WELCOME = "Welcome to Diamond.";
	private static final String MESSAGE_WRONG_FORMAT = "Please use this format: Diamond <storageFileName>.txt";
	private static final String MESSAGE_INPUT_PROMPT = "Please enter your command: ";
	private static final String MESSAGE_USABLE_COMMANDS = "Commands: add <text>, delete <line number>, display, clear, sort, search <keyword>, exit";
	private static final String MESSAGE_ADD_SUCCESS = "Task added successfully.";
	private static final String MESSAGE_DELETE_SUCCESS = "Task deleted";
	private static final String MESSAGE_NO_TASKS = "There are no tasks at the moment.";
	private static final String MESSAGE_TASKS_CLEARED = "All tasks deleted.";
	private static final String MESSAGE_SORTED = "All tasks sorted alphabetically.";
	private static final String MESSAGE_ADD_ERROR = "Error encountered when adding text. Please try again.";
	private static final String MESSAGE_DELETE_ERROR = "Error encountered when deleting task. Please try again";
	private static final String MESSAGE_DISPLAY_ERROR = "Error encountered when displaying tasks. Please try again";
	private static final String MESSAGE_CLEAR_ERROR = "Error encountered when clearing all tasks. Please try again";
	private static final String MESSAGE_SORT_ERROR = "Error encountered when sorting tasks. Please try again.";
	private static final String MESSAGE_SEARCH_ERROR = "Error encountered when searching for keyword. Please try again.";
	private static final String MESSAGE_NO_MATCH = "No match found.";
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {

		// check if command line input format is adhered to
		checkInputFormat(args);
		String fileName = args[0];
		File storageFile = new File(fileName);
		showToUser(MESSAGE_WELCOME);
		processInput(fileName, storageFile);
	}

	// exits program if input format is not adhered to
	private static void checkInputFormat(String[] args) {
		if (args.length != 1) {
			showToUser(MESSAGE_WRONG_FORMAT);
			System.exit(1);
		}
	}

	// prints text
	private static void showToUser(String textToPrint) {
		System.out.println(textToPrint);
	}
	
	// process the different commands 
	private static void processInput(String fileName, File storageFile) {
		while (true) {
			System.out.print(MESSAGE_INPUT_PROMPT);
			String command = sc.next();
			switch (command) {
			
				case "add":
					String nextLine = sc.nextLine();
					addNewLine(storageFile, nextLine);
					showToUser(MESSAGE_ADD_SUCCESS);
					break;
				
				case "delete":
					int lineNumberToDelete = sc.nextInt();
					deleteLine(storageFile, lineNumberToDelete);
					showToUser(MESSAGE_DELETE_SUCCESS);
					break;
				
				case "display":
					int linesWritten = displayAllLines(storageFile);
					if (linesWritten == 0) {
						showToUser(MESSAGE_NO_TASKS);
					}
					break;
				
				case "clear":
					clearAllText(storageFile);
					showToUser(MESSAGE_TASKS_CLEARED);
					break;

				case "sort":
					sortTask(storageFile);
					showToUser(MESSAGE_SORTED);
					break;

				case "search":
					String keyword = sc.next();
					searchText(keyword, storageFile);
					break;

				case "exit":
					System.exit(-1);
				
				default:
					showToUser(MESSAGE_USABLE_COMMANDS);
					break;
			}
		}
	}

	// appends a new line of text at the bottom of the file
	private static void addNewLine(File storageFile, String nextLine) {
		try {
			BufferedWriter addBufferedWriter = initBufferedWriter(storageFile);
			
			addBufferedWriter.write(nextLine.trim());
			addBufferedWriter.newLine();
			addBufferedWriter.close();
		}
		catch (IOException e) {
			showToUser(MESSAGE_ADD_ERROR);
		}
	}

	// deletes a line from the file based on line number
	private static String deleteLine(File storageFile, int lineToDelete) {
		try {
			File tempStorageFile = new File("tempStorageFile.txt");
			BufferedReader deleteBufferedReader = initBufferedReader(storageFile);
			BufferedWriter deleteBufferedWriter = initBufferedWriter(tempStorageFile);
			
			String line = "";
			String stringToDelete = "";
			int currentLine = 1;
			while ((line = deleteBufferedReader.readLine()) != null) {
				if (currentLine != lineToDelete) {		// write all lines except for the
					deleteBufferedWriter.write(line);		// line to be deleted in a temp file
					deleteBufferedWriter.newLine();
					currentLine++;
				}
				else {
					currentLine++;						// skip the line to be deleted
					stringToDelete = line;
				}
			}
			deleteBufferedReader.close();
			deleteBufferedWriter.close();
			storageFile.delete();
			tempStorageFile.renameTo(storageFile);
			return stringToDelete;
		}
		catch (IOException e) {
			showToUser(MESSAGE_DELETE_ERROR);
		}
		return null;
	}
					
	// displays every line in the file in a numbered sequence
	private static int displayAllLines(File storageFile) {	
		int linesWritten = 0;
		try {	
			BufferedReader displayBufferedReader = initBufferedReader(storageFile);
			String line = "";
			while ((line = displayBufferedReader.readLine()) != null) {
				linesWritten++;
				showToUser(linesWritten+ ". " +line);	
			}
			displayBufferedReader.close();
		}
		catch (IOException e) {
			showToUser(MESSAGE_DISPLAY_ERROR);
		}
		return linesWritten;	
	}

	// deletes all text in the file
	private static void clearAllText(File storageFile) {
		try {
			storageFile.delete();				// delete the whole file and
			storageFile.createNewFile();		// create a new empty file with the same name
		}
		catch (IOException e) {
			showToUser(MESSAGE_CLEAR_ERROR);
		}
	}

	private static void sortTask(File storageFile) {
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
		}
		catch (IOException e) {
			showToUser(MESSAGE_SORT_ERROR);
		}
	}

	private static void searchText(String keyword, File storageFile) {
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
		}
		return null;
	}
}
