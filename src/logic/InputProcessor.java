package logic;

import Parser.CommandParser;

/**
 * @author Bao Linh
 * This class gets the input from the UI and parse it via the Parser, then add it to the CommandQueue
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
