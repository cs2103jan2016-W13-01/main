package gui;

import java.awt.*;
import java.awt.event.*;

public class CommandWindow extends Frame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5978008643788067983L;
	TextField text;
	String cmd;

	public CommandWindow(String name) {
		super(name);
		setLayout(new FlowLayout());
		text = new TextField("", 30);
		text.addActionListener(this);
		add(text);
		setBounds(300, 300, 300, 300);
		setVisible(true);
		validate();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == text) {
			cmd = text.getText();
			text.setText("");
		}
	}

	public String getCommand() {
		return cmd;
	}
}
