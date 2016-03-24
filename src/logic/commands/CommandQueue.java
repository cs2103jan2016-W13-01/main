package logic.commands;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Bao Linh
 * The commands received from the parser will be queued up here before being executed
 */
public class CommandQueue {
	
	private static Queue<Command> commandQueue;
	
	public static Command getCommand() {
		return commandQueue.poll();
	}
	
	public static boolean addCommand(Command command) {
		return commandQueue.offer(command);
	}
	
	public static boolean isEmpty() {
		return commandQueue.isEmpty();
	}
	
	public static int getSize() {
		return commandQueue.size();
	}
	
	public static void initialize() {
		commandQueue = new LinkedList<Command>();
	}
}
