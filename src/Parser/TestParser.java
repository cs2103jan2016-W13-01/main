package parser;

import java.util.Calendar;
import java.util.Scanner;

import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.tasks.Task;
import logic.tasks.TaskUtil;

public class TestParser {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		AddParser parser = new AddParser();
		while (sc.hasNext()) {
			String input = sc.nextLine();
			CommandAdd command = (CommandAdd) parser.parse(input);
			System.out.println(command.getTask());
		}
		sc.close();
	}
}
