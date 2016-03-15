package logic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DemoUI extends JFrame implements ActionListener {
	
	private static final String PROGRAM_NAME = "Diamond";
	private JTextField textField;
	private JTextArea taskListArea;
	private JTextArea messageArea;
	
	public DemoUI() {
		super(PROGRAM_NAME);
		setBounds(200, 200, 600, 600);
		setVisible(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		textField = new JTextField(10);
		textField.setActionCommand("input");
		textField.addActionListener(this);
		
		taskListArea = new JTextArea();
		
		messageArea = new JTextArea();
		
		add(textField, BorderLayout.PAGE_END);
		add(taskListArea, BorderLayout.CENTER);
		add(messageArea, BorderLayout.LINE_END);
	}
	
	public void displayMessage(String message) {
		messageArea.append(message);
		messageArea.append("\r\n");
	}
	
	public void displayTaskList(ArrayList<String> taskList) {
		taskListArea.setText("");
		for (int i=0; i<taskList.size(); i++) {
			taskListArea.append("\r\n");
			taskListArea.append((i+1) + ". " + taskList.get(i));
		}
	}
	
	public String getCommand() {
		return textField.getText();
	}

	public String processInput() {
		String cmd = getCommand();
		String message = TaskProcessor.executeCommand(cmd);
		return message;
	}
	
	public void actionPerformed(ActionEvent e) {
		displayMessage(processInput());
		displayTaskList(TaskProcessor.getListToDisplay());
	}
}
