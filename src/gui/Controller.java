/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
    }
        
    public static void sendCmd(String command){
        DW.clear();/*
        Logic.executeCommand(cmd);
        displayFeedback();
        */
    }
    
    public static void displayFeedback(){
        displayStatus();
        displayTasks();
    }
    
    public static void displayStatus(){
        /*String status = Logic.retrieveStatusMsg();
        DW.displayStatusMsg(status);*/
    }
    
    public static void retrieveTasks(){
        /*Tasks = Logic.retrieveTasks();*/
    }
    
    public static void displayTasks(){
        /*ArrayList<String> tasks = new ArrayList<String>();
        tasks = Logic.retrieveTasks();
        DW.displayTaskList(tasks);
        */
    }
}
