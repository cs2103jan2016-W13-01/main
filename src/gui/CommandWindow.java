package gui;

import java.awt.*;
import java.awt.event.*;

public class CommandWindow extends Frame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5978008643788067983L;
	private TextField textBox;
	private TextArea displayBox;

	public CommandWindow(String name) {
		super(name);
		setLayout(new FlowLayout());
		displayBox = new TextArea(60, 100);
		add(displayBox);
		textBox = new TextField("", 60);
		textBox.addActionListener(this);
		add(textBox);
		setBounds(300, 300, 500, 500);
		setVisible(true);
		validate();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == textBox) {
			String cmd = textBox.getText();
			textBox.setText("");
		}
	}
	
	public void printMessage(String message) {
		displayBox.append(message);
		displayBox.append("\r\n");
	}
	
	public void clear() {
		displayBox.setText("");
	}

	public String getCommand() {
		String command = textBox.getText();
		textBox.setText("");
		return command;
	}
}
