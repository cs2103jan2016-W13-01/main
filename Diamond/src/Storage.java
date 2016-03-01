import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/** Class TextBuddy
 * This class provides methods to write to, delete from, view content of, clear, sort the content
 * and search for a word in a text file.
 * 
 * Assumptions:
 * 1. When adding a line, any trailing and leading space will be preserved
 *    I.E. {add    hello world   } will generate the message:
 *    {added to mytextfile.txt: "   hello world   "}
 *    this is to give users more ability to enter whatever they want, like in MS Word
 * 2. When attempting to display an empty file,
 *    a message will appear stating that the file is empty
 * 3. When attempting to delete with a line number exceeding the file's length,
 *    a message will appear stating that the line is not found
 *    and no line will be deleted
 * 4. When attempting to enter an invalid command,
 *    a message will appear stating "invalid command"
 * 5. If there is exception when deleting from/clearing/adding to the file,
 *    a message will appear stating "file cannot be modified/cleared"
 * 6. Commands beginning with "display" or "clear" or "exit" will be executed correctly
 *    if and only if there is no word following
 * 7. The sort command only allows sorting in ascending order
 */

public class Storage {

	protected String _name; // The file name, which is given as argument to main
							// method
	protected File _contentFile; // The file containing the text lines

	private static final String MESSAGE_INVALID = "invalid command: %1$s";
	private static final String MESSAGE_LINE_NOT_FOUND = "Line %1$s not found";
	private static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETED = "deleted from %1s: \"%2$s\"";
	private static final String MESSAGE_CLEARED = "all content deleted from %1$s";
	private static final String MESSAGE_SORTED_INCREASING = "%1$s is sorted in increasing order";
	private static final String MESSAGE_CANNOT_MODIFY = "File cannot be modified";
	private static final String MESSAGE_INVALID_LINE_NUMBER = "Invalid line number";
	private static final String MESSAGE_FILE_CANNOT_BE_CLEARED = "File cannot be cleared";
	private static final String MESSAGE_EMPTY_FILE = "File is empty";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";

	private static Scanner sc = new Scanner(System.in);
	
	private static Comparator<String> comparatorIgnoreSpace = new Comparator<String>() {
		public int compare(String str1, String str2) {
			return str1.trim().compareTo(str2.trim());
		}
	};
	
	// Constructor
	protected Storage(String name) throws IOException {
		_name = name;
		_contentFile = openFile(name); // Will throw IOException if file cannot
										// be created
	}
	
	public static void main(String[] args) {

		try {
			Storage textBuddy = new Storage(args[0]);
			textBuddy.getMessageWelcome();
			while (true) {
				System.out.print("command: ");
				String command = sc.nextLine();
				show(textBuddy.execute(command));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Possible command types
	private enum CommandType {
		ADD, DELETE, DISPLAY, CLEAR, SORT, SEARCH, EXIT, INVALID;
	}

	/**
	 * This method receives the command, classifies it and executes accordingly
	 * @param command
	 * @return the message after executing command
	 */
	protected String execute(String command) {

		if (isEmptyCommand(command)) {
			getMessageInvalid(command);
		}

		String commandTypeString = getFirstWord(command);

		CommandType commandType = getType(commandTypeString);

		String commandContent = removeFirstWord(command);
		String commandContentWithSpaces = removeFirstWordWithSpaces(command);
		
		switch (commandType) {
			case ADD:
				return add(commandContentWithSpaces);
			case DELETE:
				return delete(commandContent);
			case DISPLAY:
				if (!isEmptyCommand(commandContent)) {
					return String.format(MESSAGE_INVALID, command);
				}
				return display();
			case CLEAR:
				if (!isEmptyCommand(commandContent)) {
					return String.format(MESSAGE_INVALID, command);
				}
				return clear();
			case SORT:
				if (!isEmptyCommand(commandContent)) {
					return String.format(MESSAGE_INVALID, command);
				}
				return sort();
			case SEARCH:
				return search(commandContentWithSpaces);
			case EXIT:
				if (!isEmptyCommand(commandContent)) {
					return String.format(MESSAGE_INVALID, command);
				}
				System.exit(0);
			default:
				return(getMessageInvalid(command));
		}

	}

	// This method checks whether a command is empty
	private boolean isEmptyCommand(String command) {
		return command.trim().equals("");
	}

	// This method extracts the first word from a command
	private String getFirstWord(String command) {
		String commandTypeString = command.trim().split("\\s+")[0];
		return commandTypeString;
	}

	// This method extracts the part after the first word from a command with
	// leading and trailing spaces
	private String removeFirstWordWithSpaces(String command) {
		String remainingString = command.replaceAll("^\\s+", "").replaceFirst(getFirstWord(command), "");
		remainingString = remainingString.replaceFirst(" ", "");
		return remainingString;
	}

	// This method extracts the part after the first word without leading and
	// trailing spaces
	protected String removeFirstWord(String command) {
		return removeFirstWordWithSpaces(command).trim();
	}

	// This method classifies the first word of any command into types
	private CommandType getType(String commandTypeString) {
		if (commandTypeString.equalsIgnoreCase("add")) {
			return CommandType.ADD;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return CommandType.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
			return CommandType.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return CommandType.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return CommandType.EXIT;
		} else if (commandTypeString.equalsIgnoreCase("sort")) {
			return CommandType.SORT;
		} else if (commandTypeString.equalsIgnoreCase("search")) {
			return CommandType.SEARCH;
		} else {
			return CommandType.INVALID;
		}
	}

	// Shows an invalid command message
	private String getMessageInvalid(String command) {
		String message = String.format(MESSAGE_INVALID, command);
		return message;
	}

	// Shows a successful addition message
	private String getMessageAdded(String textLine) {
		String message = String.format(MESSAGE_ADDED, _name, textLine);
		return message;
	}

	// Shows a successful deletion message
	private String getMessageDeleted(String textLine) {
		String message = String.format(MESSAGE_DELETED, _name, textLine);
		return message;
	}

	// Shows a successful clearance message
	private String getMessageCleared() {
		String message = String.format(MESSAGE_CLEARED, _name);
		return message;
	}

	// Shows a welcome message
	private String getMessageWelcome() {
		String message = String.format(MESSAGE_WELCOME, _name);
		return message;
	}

	/**
	 * This methods add a line specified in the command to the end of the text
	 * file
	 * 
	 * @param line
	 *            the command entered by the user
	 */
	protected String add(String line) {
		try {
			PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter(_contentFile, true)));
			wr.println(line);
			wr.close();
			return getMessageAdded(line);
		} catch (IOException e) {
			return MESSAGE_CANNOT_MODIFY;
		}
	}

	/**
	 * This methods delete a line specified in the command from the text file by
	 * creating a new empty file, then copy every line from our text file to the
	 * new file except the line to be deleted, then rename the new file into our
	 * file name
	 * 
	 * @param line
	 *            the command entered by the user
	 */
	protected String delete(String line) {
		try {
			int i = getLineNumber(line);

			// Only positive integer line number is valid
			if (i < 0) {
				return MESSAGE_INVALID_LINE_NUMBER;
			}
			
			File tempFile = openFile("~" + _name);
			String lineToBeDeleted = copyToTempFile(i, tempFile);
			_contentFile.delete();
			tempFile.renameTo(_contentFile);

			if (lineToBeDeleted == null) {
				return String.format(MESSAGE_LINE_NOT_FOUND, i);
			} else {
				return getMessageDeleted(lineToBeDeleted); // Successful deletion
			}
		} catch (IOException e) {
			return MESSAGE_CANNOT_MODIFY;
		}
	}

	/**
	 * This method copies from content file to a target temporary file line by
	 * line except the line whose number is specified and returns the deleted line
	 * 
	 * @param lineNumber
	 *            the number of the line to be deleted
	 * @param tempFile
	 *            the temporary target file
	 * @return the deleted line as a String
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private String copyToTempFile(int lineNumber, File tempFile) throws IOException {
		
		int j = 1;
		String lineToBeDeleted = null;
		String lineToCopy = null;
		PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)));
		BufferedReader br = new BufferedReader(new FileReader(_contentFile));
		
		// read from current file and copy to temp file
		while (true) {
			lineToCopy = br.readLine();
			if (lineToCopy == null) {
				break;
			}
			if (j == lineNumber) {
				lineToBeDeleted = lineToCopy;
			} else {
				wr.println(lineToCopy);
			}
			j++;
		}
		br.close();
		wr.close();
		
		return lineToBeDeleted;
	}

	/**
	 * Extract the line number from the command, returns -1 if it is not a positive integer
	 * @param lineNumberString
	 * @return the line number or -1 if it is not a positive integer
	 */
	private int getLineNumber(String lineNumberString) {
		try {
			int lineNumber = Integer.parseInt(lineNumberString);
			if (lineNumber > 0) {
				return lineNumber;
			}
		} catch (NumberFormatException e) {
		}
		return -1;
	}

	// display the content of the text file line by line
	protected String display() {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(_contentFile));
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while (true) {
				String line = bf.readLine();
				if (line == null) {
					break;
				}
				sb.append(++i);
				sb.append(". " + line + "\r\n");
			}
			bf.close();
			if (i == 0) {
				return MESSAGE_EMPTY_FILE;
			} else {
				return sb.toString();
			}
		} catch (IOException e) {
			return e.toString();
		}
	}

	// Clear all the file's content
	protected String clear() {
		try {
			File tempFile = openFile("~" + _name);
			_contentFile.delete();
			tempFile.renameTo(_contentFile);
			return getMessageCleared();
		} catch (IOException e) {
			return MESSAGE_FILE_CANNOT_BE_CLEARED;
		}
	}
	
	/**
	 * This method sorts the lines the file in lexicographical order
	 * @param command
	 * @return message whether the sort is successful
	 */
	protected String sort() {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(_contentFile));
			PriorityQueue<String> sortedLines = new PriorityQueue<String>(10, comparatorIgnoreSpace);
			
			// add lines into the queue
			while (true) {
				String line = bf.readLine();
				if (line == null) {
					break;
				}
				sortedLines.add(line);
			}
			bf.close();
			
			// clearing the file
			clear();
			
			// print lines back to the file
			PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter(_contentFile)));
			while (!sortedLines.isEmpty()) {
				wr.println(sortedLines.poll());
			}
			wr.close();
			return String.format(MESSAGE_SORTED_INCREASING, _name);
		} catch (IOException e) {
			return e.toString();
		}
		
	}
	
	/**
	 * This method searches for a word in the file and returns the lines containing it
	 * @param word
	 * @return the lines containing the word
	 */
	protected String search(String word) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(_contentFile));
			StringBuilder sb = new StringBuilder();
			int i=0;
			
			// check for the word in each line and print the lines containing it
			while (true) {
				String line = bf.readLine();
				if (line == null) {
					break;
				}
				if (containsIgnoreCase(line, word)) {
					sb.append(++i);
					sb.append(". " + line + "\r\n");
				}
			}
			bf.close();
			
			return sb.toString();
		} catch (IOException e) {
			return e.toString();
		}
	}
	
	private boolean containsIgnoreCase(String container, String containee) {
		return container.toLowerCase().contains(containee.toLowerCase());
	}

	protected static void show(String s) {
		System.out.println(s);
	}

	/**
	 * Open a file with the specified name if it exists, otherwise create a new
	 * file and open it
	 * 
	 * @param name
	 *            the name of the file to be opened
	 * @return the file with the specified name
	 */
	protected static File openFile(String name) throws IOException {
		File newFile = new File(name);
		newFile.createNewFile();
		return newFile;
	}
}
