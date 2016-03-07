package gui;

/**
 * @@author Tan Fengji(A0129845U)
 * */

/**
 * This is a class to build GUI directly
 * */
public class View{
private View GUI;
private String CMD;
 private View(){
 }
 
 protected View getInstance() {
	 buildGUI();
	 return GUI;
 }
 
 protected void buildGUI() { 
	 loadImage();
	 loadTextfield();
	 showAll();
 }
 
 
 private void loadImage() {
 }
 
 private void loadTextfield(){}
 
 private String readTextfield(){
	 return CMD;
 }

 protected void showAll() {
 }
}


