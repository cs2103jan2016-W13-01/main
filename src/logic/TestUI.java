package logic;

import java.util.Scanner;

import gui.*;

public class TestUI {
	
	public static void main(String[] args) {
		// CommandWindow cw = new CommandWindow("Command");
		DisplayWindow dw = new DisplayWindow("Display");
		Scanner sc = new Scanner(System.in);
		while (true) {
			String cmd = sc.nextLine();
			//String cmd = cw.getCommand();
			if (cmd.equals("exit")) {
				System.exit(0);
			}
			//System.out.println(cmd);
			dw.displayTask(cmd);
		}
	}
}
