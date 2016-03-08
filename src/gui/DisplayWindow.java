package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.Task;
import logic.TaskProcessor;

public class DisplayWindow extends Frame implements ActionListener {
	
	TextArea task;
	TextField text;
	String cmd;

	public DisplayWindow(String name) {
		super(name);
		setLayout(new FlowLayout());
		text = new TextField("", 30);
		text.addActionListener(this);
		add(text);
		task = new TextArea("");
		add(task);
		setBounds(500, 500, 500, 500);
		setVisible(true);
		validate();
	}
	
	public static void main(String[] args) {
		DisplayWindow dw = new DisplayWindow("window");
	}

	public void displayTask(String event) {
		task.setText(event);
	}

	public String getCommand() {
		return cmd;
	}

	public void clearTasks() {
		text.setText("");
	}

	public void actionPerformed(ActionEvent e) {
		int taskCounter = 0;
		cmd = text.getText();
		text.setText("");
		task.setText("");
		String message = TaskProcessor.getReturnMessage(cmd);
		task.append(message);
		for (Task t: TaskProcessor.getTaskList()) {
			taskCounter++;
			task.append(taskCounter + ". " + t.toString());
		}
	}
}