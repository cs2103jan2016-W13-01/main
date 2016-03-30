/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import logic.ExecutedCommands;
import logic.Response;
import logic.TaskProcessor;

import Storage.Storage;

import java.util.ArrayList;
/**
 *
 * @author tfj
 */
public class Controller {
	
    public static DisplayWindow DW;
    
    // @@author A0112184R
    public static void main(String args[]){
        initialize();
    }
	// @@author
    
    /* @@author A0112184R
     * initialize the display window and all logic's components
     */
	public static void initialize() {
    	DW = new DisplayWindow();
        DW.setVisible(true);
		TaskProcessor.initialize();
		displayTasks(TaskProcessor.getListToDisplay(), Storage.getFloatBeginOnIndexList());
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
           int floatBegin = Storage.getFloatBeginOnIndexList();
           displayStatus(status);
           displayTasks(tasks, floatBegin);
		}
    
    public static void displayStatus(String status){
        DW.displayStatusMsg(status);
    }
    
    
    public static void displayTasks(ArrayList<String> tasks, int floatBegin){
		DW.displayTaskList(tasks, floatBegin);
    }
}
