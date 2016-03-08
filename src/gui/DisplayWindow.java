package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		if (e.getSource() == text) {
			cmd = text.getText();
			text.setText("");
		}
	}
}