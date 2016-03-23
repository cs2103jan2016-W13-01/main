package logic;

import java.util.Stack;

import logic.commands.Command;

/**
 * @author Bao Linh
 *
 */
public class ExecutedCommands {
	
	public static Stack<Command> executedCommands;
	
	public static Command getLatestCommand() {
		return executedCommands.pop();
	}
	
	public static boolean addCommand(Command command) {
		return executedCommands.add(command);
	}
	
	public static boolean isEmpty() {
		return executedCommands.isEmpty();
	}
	
	public static int getSize() {
		return executedCommands.size();
	}
	
	public static void initialize() {
		executedCommands = new Stack<Command>();
	}
}
