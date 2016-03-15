package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Storage.Storage;
import gui.CommandWindow;

public class Diamond {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DemoUI GUI;
	
	private static final String MESSAGE_TASK_LIST = "Current task list:";
	private static final String MESSAGE_NO_TASK = "No task to show.";

	public static void main(String args[]) {
		
		GUI = new DemoUI();
		TaskProcessor.initialize();
		GUI.displayTaskList(TaskProcessor.getListToDisplay());
	}
}
