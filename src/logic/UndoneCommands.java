package logic;

import java.util.Stack;

import logic.commands.Command;

/* @@author A0112184R
 *
 */
public class UndoneCommands {
	
	public static Stack<Command> undoneCommands;
	
	public static Command getLatestCommand() {
		return undoneCommands.pop();
	}
	
	public static boolean addCommand(Command command) {
		return undoneCommands.add(command);
	}
	
	public static boolean isEmpty() {
		return undoneCommands.isEmpty();
	}
	
	public static int getSize() {
		return undoneCommands.size();
	}
	
	public static void initialize() {
		undoneCommands = new Stack<Command>();
	}
}
