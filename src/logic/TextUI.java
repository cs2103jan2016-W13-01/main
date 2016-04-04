/* @@author A0112184R
 * This class is used to test the program when the GUI was in progress
 */
package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import Parser.CommandParser;
import logic.commands.Command;

public class TextUI {
	
	private static Scanner sc;
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		initialize();
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			Response response = executeInput(input);
			displayResponse(response, System.out);
		}
		sc.close();
	}
	
	public static void mainFileToFile(File inputFile, File outputFile) throws FileNotFoundException {
		sc = new Scanner(inputFile);
		PrintStream pr = new PrintStream(outputFile);
		initialize();
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			Response response = executeInput(input);
			displayResponse(response, pr);
		}
		sc.close();
		pr.close();
	}

	private static Response executeInput(String input) {
		Command command = CommandParser.parseInput(input);
		Response response = TaskProcessor.executeCommand(command);
		return response;
	}
	
	public static void displayResponse(Response response, PrintStream printer) {
		if (response != null) {
			displayMessage(response.getMessage(), printer);
			displayTaskList(response.getTaskList(), printer);
		}
	}
	
	public static void displayMessage(String message, PrintStream printer) {
		printer.println(message);
	}
	
	public static void displayTaskList(ArrayList<String> taskList, PrintStream printer) {
		if (taskList != null) {
			if (!taskList.isEmpty()) {
				int lineNum = 0;
				for (String task: taskList) {
					lineNum++;
					printer.print(lineNum + ". ");
					printer.println(task);
				}
			} else {
				printer.println("No tasks to show");
			}
		} else {
			printer.println("Task list not loaded");
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
