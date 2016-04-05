/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import logic.Response;
import logic.TaskProcessor;

import java.util.ArrayList;
/**
 *
 * @author tfj
 */
public class Controller {
	
    private static final String PENDING = "pending";
	private static final String COMPLETED = "completed";
	private static final String UPCOMING = "upcoming";
	private static final String ALL = "all";
	public static DisplayWindow DW;
    
    // @@author A0112184R
    public static void main(String args[]) throws Exception{
        initialize();
    }
	// @@author
    
    /* @@author A0112184R
     * initialize the display window and all logic's components
     */
	public static void initialize() throws Exception {
    	DW = new DisplayWindow();
        DW.setVisible(true);
		TaskProcessor.initialize();
		displayTasks(TaskProcessor.getListToDisplay(), ALL);
	}
    // @@author    
    
	public static void sendCmd(String command){
        DW.clear(); 
        Response response = TaskProcessor.executeInput(command);
        displayFeedback(response);
    }
    
    public static void displayFeedback(Response response){
           String status = response.getMessage();
           ArrayList<String> tasks = response.getTaskList();
           String type = response.getType();
           displayStatus(status);
           displayTasks(tasks, type);
		}
    
    public static void displayStatus(String status){
        DW.displayStatusMsg(status);
    }
    
    
    public static void displayTasks(ArrayList<String> tasks, String type){
        if (type.equals(ALL))
		DW.displayAllTaskList(tasks);
        else if (type.equals(UPCOMING))
		DW.displayUpcomingTaskList(tasks);
        else if (type.equals(COMPLETED))
		DW.displayCompletedTaskList(tasks);        
        else if (type.equals(PENDING))
		DW.displayPendingTaskList(tasks);   
    }
}
