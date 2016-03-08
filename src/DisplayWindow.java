package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DisplayWindow extends Frame implements ActionListener{
	TextArea task;
	TextField text;
	String cmd;

DisplayWindow(String name){
	super(name);
	setLayout(new FlowLayout());
	task = new TextArea("");
	add(task);
	text = new TextField("", 60);
	text.addActionListener(this);
	add(text);
	setBounds(500, 500, 500, 500);
	setVisible(true);
	validate();
	}

public void displayTask(String event){
	task.append(event);		
}

public String getCommand() {
	return cmd;
}
public void clearWindow(){
	text.setText("");
	}

public void actionPerformed(ActionEvent e)
{
 if(e.getSource()==text){
	 cmd = text.getText();
	 text.setText("");
 }
}
}