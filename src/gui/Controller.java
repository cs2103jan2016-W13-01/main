/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import logic.ExecutedCommands;
import logic.InputQueue;
import logic.ResponseQueue;
import logic.Response;
import logic.TaskProcessor;
        
import java.util.ArrayList;
/**
 *
 * @author tfj
 */
public class Controller {
    public static DisplayWindow DW;
    
    // @@author a0112184r
    public static void main(String args[]){
        initialize();
    }
	// @@author
    
    /* @@author a0112184r
     * initialize the display window and all logic's components
     */
	public static void initialize() {
    	DW = new DisplayWindow();
        DW.setVisible(true);
		ExecutedCommands.initialize();
		TaskProcessor.initialize();
		displayTasks(TaskProcessor.getListToDisplay());
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
           displayStatus(status);
           displayTasks(tasks);
		}
    
    public static void displayStatus(String status){
        DW.displayStatusMsg(status);
    }
    
    
    public static void displayTasks(ArrayList<String> tasks){
        DW.displayTaskList(tasks);
    }
}
