# a0112184runused
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
###### src\logic\Diamond.java
``` java
 * Reason: we omitted the parallel execution scheme
 */
public class Diamond {
	
	private static final String MESSAGE_TASK_LIST = "Current task list:";
	private static final String MESSAGE_NO_TASK = "No task to show.";

	public static void main(String args[]) throws InterruptedException {
		
		TextUI.initialize();
		InputProcessor.initialize();
		TaskProcessor.initialize();
		
		//Thread GUI = new Thread(() -> Controller.main(null), "GUI");
		Thread textUI = new Thread(() -> TextUI.main(null), "textUI");
		Thread inputProc = new Thread(() -> InputProcessor.main(null), "inputProc");
		Thread taskProc = new Thread(() -> TaskProcessor.main(null), "taskProc");
		
		//GUI.start();
		textUI.start();
		inputProc.start();
		taskProc.start();
		
		//GUI.join();
		textUI.join();
		inputProc.join();
		taskProc.join();
	}
}
```
###### src\logic\InputProcessor.java
``` java
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
```
###### src\logic\InputQueue.java
``` java
 * This class stores the inputs that the UI sends to the Logic
 * Reason: we omitted the parallel execution scheme
 */
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
###### src\logic\TextUI.java
``` java
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
```