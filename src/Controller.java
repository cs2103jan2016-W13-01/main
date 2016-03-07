package gui;

/*
 * @@author Tan Fengji (A0129845U)
 */

import Parser;
import java.util.*;
/**
 * This is a class to detect users' action and control the behavior of GUIView
 * */

public class Controller {
 private static View GUI_VIEW = View.getInstance();
 
 public View getView() {
  return GUI_VIEW;
 }
 
 public static void main(String args[]) {
  start();
  close();
 }

 public static void start() throws Exception {
  while (true){
	  String command = getCommand();
	  
  }  
 }
 
 /*
  * Get the user input from the textfield in the GUI and handle the input
  */
 private String getCommand() {
	 return GUI_VIEW.readTextfield();
 }


 private void execute(String command) throws Exception {
	 Parser.parseCommand(command);
	 GUIView.showall();
  /**String commandType = getCommand();
  switch (commandType) {
  case "add":
   executeAdd(command);
   break;
  case "delete":
   executeDelete(command);
   break;
  case "display":
   executeAll(command);
   break;
  case "edit":
   executeUpdate(command);
   break;
  case "show":
   executeShow(command);
   break;
  case "exit":
   executeExit(command);
   break;
  case "clear":
   executeClear(command);
   break;
  default:
   break;
  }
  **/
 }
 /*----------------- LIST OF EXECUTIONS TO THE COMMAND INPUT ----------------*/
 
 /* Each execution method will show the corresponding list to the user in the UI view.
  * @param String command
  */
 private void executeClear(String command) throws Exception {
  Parser.parseCommand(command);
  GUI_VIEW.showAll();
 }

 private void executeAdd(String command) throws Exception {
	  Parser.parseCommand(command);
	  GUI_VIEW.showAll();
 }
}
