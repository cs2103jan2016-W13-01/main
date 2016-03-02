package gui;

/**
 * @@author Tan Fengji(A0129845U)
 * */

/**
 * This is a class to build GUI directly
 * */
public class View{
private View GUI;
 private View(){
 }
 
 protected View getInstance() {
	 buildGUI();
	 return GUI;
 }
 
 protected void buildGUI() { 
	 loadImage();
	 showAll();
 }

 private void loadImage() {
 }


 protected void showAll() {
 }
}


