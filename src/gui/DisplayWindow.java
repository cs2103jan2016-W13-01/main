package gui;

import java.awt.*;

class DisplayWindow extends Frame{
	TextArea text;

DisplayWindow(String name){
	super(name);
	setLayout(new FlowLayout());
	text = new TextArea("");
	add(text);
	setBounds(500, 500, 500, 500);
	setVisible(true);
	validate();
	}

public void displayTask(String task){
	text.setText(task);		
}

public void clearTasks(){
	text.setText("");
	}
}