package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import logic.commands.Command;
import logic.commands.CommandHelp;
import logic.commands.CommandInvalid;
import logic.tasks.*;
import parser.CommandParser;
import storage.StorageController;

/* @@author A0112184R
 * Class TaskProcessor
 * This class contains methods to execute all the user commands and issue commands to other components.
 * Key methods so far:
 *     - executeCommand(Command): execute the Command object by calling Command.execute()
 *     - executeInput(String): execute the input by parsing it and call executeCommand
 *     - initialize: initialize the StorageController and all the components
 */
public class TaskProcessor {
	
	/*
	private static final String MESSAGE_DISPLAY_ERROR = "Error encountered when displaying tasks. Please try again";
	private static final String MESSAGE_CLEAR_ERROR = "Error encountered when clearing all tasks. Please try again";
	private static final String MESSAGE_SORT_ERROR = "Error encountered when sorting tasks. Please try again.";
	*/

	private static ArrayList<String> listToDisplay;

	
	public static ArrayList<String> getListToDisplay() {
		loadIntoDisplayList(StorageController.getDisplayList());
		return listToDisplay;
	}
	
	public static Response executeInput(String input) {
		LogicLogger.log(Level.INFO, "Executing input: " + input);
		Command command = CommandParser.parseInput(input);
		return executeCommand(command);
	}

	public static Response executeCommand(Command command) {
		String message = command.execute();
		if (command instanceof CommandHelp) {
			ArrayList<String> helpContent = ((CommandHelp) command).getHelpContent();
			return new Response(message, "help", helpContent);
		}
		if (command instanceof CommandInvalid) {
			return new Response(command.execute(), "no change", null);
		}
		ArrayList<String> taskList = getListToDisplay();
		return new Response(message, StorageController.getTabType(), taskList);
	}
	
	public static void initialize() {
		listToDisplay = new ArrayList<String>();
		ExecutedCommands.initialize();
		UndoneCommands.initialize();
		LogicLogger.initialize();
		try {
			LogicLogger.log(Level.INFO, "Initializing StorageController");
			StorageController.initialize();
			loadIntoDisplayList(StorageController.getDisplayList());
		} catch (IOException e) {
			LogicLogger.log(Level.SEVERE, "Error when initializing StorageController");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private static void loadIntoDisplayList(ArrayList<Task> taskList) {
		LogicLogger.log(Level.INFO, "Loading list to display from StorageController");
		listToDisplay.clear();
		for (Task task: taskList) {
			assert task != null : "Some task in the task list is null";
			listToDisplay.add(task.toString());
		}
	}
}
