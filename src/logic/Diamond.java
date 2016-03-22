package logic;

import gui.Controller;

public class Diamond {
	
	private static final String MESSAGE_TASK_LIST = "Current task list:";
	private static final String MESSAGE_NO_TASK = "No task to show.";

	public static void main(String args[]) throws InterruptedException {
		Thread GUI = new Thread(() -> Controller.main(null), "GUI");
		Thread inputProc = new Thread(() -> InputProcessor.main(null), "inputProc");
		Thread taskProc = new Thread(() -> TaskProcessor.main(null), "taskProc");
		
		GUI.start();
		inputProc.start();
		taskProc.start();
		
		GUI.join();
		inputProc.join();
		taskProc.join();
	}
}
