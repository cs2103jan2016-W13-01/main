# A0112184Runused
###### src\logic\commands\CommandQueue.java
``` java
 * The commands received from the parser will be queued up here before being executed
 * reason for unused: we decided to omit the command pattern and will not go for the parallel execution scheme
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
```
###### src\logic\InputProcessor.java
``` java
 * This class gets the input from the UI and parse it via the Parser, then add it to the CommandQueue
 * Reason: we omitted the parallel execution scheme
 */
package logic;

import logic.commands.Command;
import logic.commands.CommandQueue;
import parser.CommandParser;

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
```
###### src\logic\InputQueue.java
``` java
 * This class stores the inputs that the UI sends to the Logic
 * Reason: we omitted the parallel execution scheme
 */
package logic;

import java.util.LinkedList;
import java.util.Queue;

public class InputQueue {
	
	private static Queue<String> inputQueue;
	
	public static boolean addInput(String input) {
		return inputQueue.offer(input);
	}
	
	public static boolean isEmpty() {
		return inputQueue.isEmpty();
	}
	
	public static String getInput() {
		return inputQueue.poll();
	}
	
	public static void initialize() {
		inputQueue = new LinkedList<String>();
	}
}
```
###### src\logic\ResponseQueue.java
``` java
 * This class stores the responses for the UI to display
 * Reason: we omitted the parallel execution scheme
 */
package logic;

import java.util.LinkedList;
import java.util.Queue;

public class ResponseQueue {
	
	private static Queue<Response> responseQueue;
	
	public static Response getResponse() {
		return responseQueue.poll();
	}
	
	public static boolean isEmpty() {
		return responseQueue.isEmpty();
	}
	
	public static boolean addResponse(Response response) {
		return responseQueue.offer(response);
	}
	
	public static void initialize() {
		responseQueue = new LinkedList<Response>();
	}
}
```
