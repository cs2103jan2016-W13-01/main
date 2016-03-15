package Parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import logic.CommandDetails;
import logic.CommandType;
import logic.Task;

public class CommandParser {

	public ArrayList<String> list;
	public static SimpleDateFormat sdf;

	public CommandParser() {
		list = new ArrayList<String>();
		sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		sdf.setLenient(false);
	}

	/**
	 * Requires input format add title date
	 */
	public static CommandDetails parseInput(String input) {
		input = input.trim();
		input = input.replaceAll("\\s+", " ");
		String[] inputTokens = input.split(" ");
		CommandType cmd = getCmd(inputTokens[0]);
		Task task = null;
		int inputNum;
		CommandDetails cmdDetails;

		switch (cmd) {

			case ADD:
				task = getTask(input);
				cmdDetails = new CommandDetails(cmd, task);
				break;
	
			case DELETE:
				inputNum = getInputNum(inputTokens);
				if (inputNum < 0) {
					cmd = CommandType.INVALID;
				}
				cmdDetails = new CommandDetails(cmd, task);
				cmdDetails.setInputNum(inputNum);
				break;
	
			case EDIT:
				task = getTask(input);
				inputNum = getInputNum(inputTokens);
				if (inputNum < 0) {
					cmd = CommandType.INVALID;
				}
				cmdDetails = new CommandDetails(cmd, task);
				cmdDetails.setInputNum(inputNum);
				break;
	
			case UNDO:
				cmdDetails = new CommandDetails(cmd, task);
				break;
	
			case MARK:
				inputNum = getInputNum(inputTokens);
				if (inputNum < 0) {
					cmd = CommandType.INVALID;
				}
				cmdDetails = new CommandDetails(cmd, task);
				cmdDetails.setInputNum(inputNum);
				break;
	
			case UNMARK:
				inputNum = getInputNum(inputTokens);
				if (inputNum < -1) {
					cmd = CommandType.INVALID;
				}
				cmdDetails = new CommandDetails(cmd, task);
				cmdDetails.setInputNum(inputNum);
				break;
	
			default:
				cmdDetails = new CommandDetails(cmd, task);

		}

		return cmdDetails;
	}

	private static Task getTask(String input) {

		String[] inputTokens = input.split(" ", 2);
		String title = titleParser.getTitle(inputTokens[1]);
		Date date = dateParser.getDate(inputTokens[1]);

		Task task = new Task(title, date);
		return task;
	}

	private static int getInputNum(String[] inputTokens) {
		String inputNum = inputTokens[1];
		int num;
		try {
			num = Integer.parseInt(inputNum);
		} catch (Exception e) {
			num = -1;
		}
		System.out.println("deleted num = " + num);
		return num;
	}

	private static String getCommandDescription(String[] inputTokens) {
		String description = "";
		for (int i = 0; i < inputTokens.length; i++) {
			if (inputTokens[i].charAt(0) == '/') {
				StringBuilder sb = new StringBuilder(inputTokens[i]);
				sb = sb.deleteCharAt(0);
				description = sb.toString();
				break;
			}
		}
		return description;
	}

	private static CommandType getCmd(String string) {
		string = string.toLowerCase();
		switch (string) {
		case "a":
		case "add":
			return CommandType.ADD;
		case "d":
		case "delete":
			return CommandType.DELETE;
		case "u":
		case "undo":
			return CommandType.UNDO;
		case "m":
		case "mark":
			return CommandType.MARK;
		case "um":
		case "unmark":
			return CommandType.UNMARK;
		case "e":
		case "edit":
			return CommandType.EDIT;

		default:
			return CommandType.INVALID;
		}
	}
}
