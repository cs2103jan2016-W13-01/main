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
        while (true){
            sendCmd();
            /*retrieveStatusMsg();
            displayTasks();
        }*/
    }}
        
    public static void sendCmd(){
  
        String cmd = DW.getCmd();
        DW.clear();/*
        Logic.executeCommand(cmd);*/
    }
    
    public static void retrieveStatusMsg(){
        /*String status = Logic.retrieveStatusMsg();
        DW.displayStatus(status);*/
    }
    
    public static void displayTasks(){
        /*ArrayList<String> tasks = new ArrayList<String>();
        Tasks = Logic.retrieveTasks();
        int length = tasks.length();
        for (int i = 0; i< length; i++){
            DW.jTextField1.append(i + ". " + task + "\r\n");
        }
        */
    }
}
