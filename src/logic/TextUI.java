package logic;

import java.util.ArrayList;
import java.util.Scanner;

import Parser.CommandParser;
import logic.commands.Command;

/* @@author A0112184R-unused
 * This class is used to test the program when the GUI was in progress
 * Reason: we do not use it now that we have the GUI
 */
public class TextUI {
	
	private static Scanner sc;
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		initialize();
		while (true) {
			String input = sc.nextLine();
			Command command = CommandParser.parseInput(input);
			Response response = TaskProcessor.executeCommand(command);
			displayResponse(response);
		}
	}
	
	public static void getAndDisplay() {
		Response response = ResponseQueue.getResponse();
		displayResponse(response);
	}
	
	public static void displayResponse(Response response) {
		if (response != null) {
			displayMessage(response.getMessage());
			displayTaskList(response.getTaskList());
		}
	}
	
	public static void displayMessage(String message) {
		System.out.println(message);
	}
	
	public static void displayTaskList(ArrayList<String> taskList) {
		if (taskList != null) {
			if (!taskList.isEmpty()) {
				int lineNum = 0;
				for (String task: taskList) {
					lineNum++;
					System.out.print(lineNum + ". ");
					System.out.println(task);
				}
			} else {
				System.out.println("No tasks to show");
			}
		} else {
			System.out.println("Task list not loaded");
		}
	}
	
	public static void getInput() {
		String input = sc.nextLine();
		InputQueue.addInput(input);
	}
	
	public static void initialize() {
		ExecutedCommands.initialize();
		TaskProcessor.initialize();
	}
}
