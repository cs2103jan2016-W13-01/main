package logic;

import java.util.Stack;

import logic.commands.Command;

/* @@author A0112184R
 * This stack contains the executed commands
 * Each time an undo is executed, the top of the stack is popped out and undone
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
