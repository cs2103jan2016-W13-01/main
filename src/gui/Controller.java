/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import logic.InputQueue;
import logic.ResponseQueue;
import logic.Response;
        
import java.util.ArrayList;
/**
 *
 * @author tfj
 */
public class Controller {
    public static DisplayWindow DW;
    
    public static void main(String args[]){
        DW = new DisplayWindow();
        DW.setVisible(true);
        displayFeedback();
    }
        
    public static void sendCmd(String command){
        DW.clear();
        InputQueue.addInput(command);
    }
    
    public static void displayFeedback(){
       while (true){
       while (ResponseQueue.isEmpty() == false) {
           Response response = ResponseQueue.getResponse();
           String status = response.getMessage();
           ArrayList<String> tasks = response.getTaskList();
           displayStatus(status);
           displayTasks(tasks);
		}}}
    
    public static void displayStatus(String status){
        DW.displayStatusMsg(status);
    }
    
    
    public static void displayTasks(ArrayList<String> tasks){
        DW.displayTaskList(tasks);
    }
}
