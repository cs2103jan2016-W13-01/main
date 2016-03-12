package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CommandWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5978008643788067983L;
	private TextField textBox;
	private TextArea displayBox;

	public CommandWindow(String name) {
		super(name);
		setLayout(new GridBagLayout());
		
		addFileButton();
		addEditButton();
		addOptionButton();
		
		addTextArea();
		addTextField();
		
		setBounds(300, 300, 500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		validate();
	}
	
	private void addFileButton() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.33;
		c.gridx = 0;
		c.gridy = 0;
		add(new JButton("File"), c);
	}
	
	private void addEditButton() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.33;
		c.gridx = 1;
		c.gridy = 0;
		add(new JButton("Edit"), c);
	}
	
	private void addOptionButton() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.33;
		c.gridx = 2;
		c.gridy = 0;
		add(new JButton("Option"), c);
	}
	
	private void addTextArea() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 80;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		displayBox = new TextArea("Welcome to Diamond!");
		add(displayBox, c);
	}
	
	private void addTextField() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.NORTHWEST; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		textBox = new TextField("");
		textBox.addActionListener(this);
		add(textBox, c);
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
