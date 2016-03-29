package logic;

import gui.Controller;

/* @@author a0112184r-unused
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
