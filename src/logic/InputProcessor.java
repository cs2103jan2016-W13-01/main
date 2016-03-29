package logic;

import Parser.CommandParser;
import logic.commands.Command;
import logic.commands.CommandQueue;

/* @@author a0112184r-unused
 * This class gets the input from the UI and parse it via the Parser, then add it to the CommandQueue
 * Reason: we omitted the parallel execution scheme
 */
public class InputProcessor {
	
	public static void getAndParseInput() {
		String input = InputQueue.getInput();
		if (input != null) {
			Command command = CommandParser.parseInput(input);
			CommandQueue.addCommand(command);
		}
	}
	
	public static void main(String[] args) {
		while (true) {
			getAndParseInput();
		}
	}
	
	public static void initialize() {
		InputQueue.initialize();
	}
}
