package logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/* @@author A0121535R
 * This class contains details for help commands
 */
public class CommandHelp implements Command{
	
	private static final String TOPIC_SEPARATOR = "\r\n==================================================================\r\n";
	private static final String MESSAGE_HELP = "Welcome to help topics!";
	private static final String MESSAGE_HELP_TOPIC = "Welcome to help topic for: %s!";
	private static final String HELP_FILE_NAME = "./src/document/help.txt";
	private static final String MESSAGE_NO_HELP_CONTENT = "Sorry, the help contents will be included in our next version!";
	private static final String MESSAGE_NO_HELP_TOPIC = "Sorry, there are no help contents for this topic.";
	
	private String topic;

	public CommandType getType() {
		return CommandType.HELP;
	}
	
	public CommandHelp() {
		topic = null;
	}
	
	public CommandHelp(String topicName) {
		topic = topicName;
	}
	
	public ArrayList<String> getHelpContent() {
		if (topic != null) {
			return getHelpTopic(topic);
		} else {
			return getHelpTopics();
		}
	}
	
	public ArrayList<String> getHelpTopics() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(new File(HELP_FILE_NAME));
			while (sc.hasNextLine()) {
			    appendHelpTopic(result, sc);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result.add(MESSAGE_NO_HELP_CONTENT);
			return result;
		}
	}

	private void appendHelpTopic(ArrayList<String> list, Scanner sc) {
		String line = sc.nextLine();
		if (line.trim().equals("{")) {
			StringBuilder sb = new StringBuilder();
			line = sc.nextLine();
			while (!line.trim().equals("}")) {
				sb.append(line);
				sb.append("\r\n");
				line = sc.nextLine();
			}
			sb.append(TOPIC_SEPARATOR);
			list.add(sb.toString());
		}
	}
	
	public ArrayList<String> getHelpTopic(String topicName) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(new File(HELP_FILE_NAME));
			boolean found = false;
			while (sc.hasNextLine()) {
				boolean match = foundAndAppend(topicName, result, sc);
				if (!found) {
					found = match;
				}
			}
			sc.close();
			if (found) {
				return result;
			} else {
				result.add(MESSAGE_NO_HELP_TOPIC);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
			result.add(MESSAGE_NO_HELP_CONTENT);
			return result;
		}
	}

	private boolean foundAndAppend(String topicName, ArrayList<String> list,
										Scanner sc)
	{
		boolean found = false;
		String open = sc.nextLine();
		if (open.trim().equals("{")) {
			String line = sc.nextLine();
			found = line.split(":")[1].trim().equals(topicName);
			if (found) {
				StringBuilder sb = new StringBuilder();
				while (!line.trim().equals("}")) {
					sb.append(line);
					sb.append("\r\n");
					line = sc.nextLine();
				}
				sb.append(TOPIC_SEPARATOR);
				list.add(sb.toString());
			}
		}
		return found;
	}
	
	public String execute() {
		if (topic != null) {
			return String.format(MESSAGE_HELP_TOPIC, topic);
		}
		return MESSAGE_HELP;
	}
	
	public String undo() {
		return null;
	}

}
