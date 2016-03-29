package logic.commands;

/**
 * @author Bao Linh
 * This class contains details for the "set location" commands
 */

import Storage.Storage;

public class CommandSetLocation implements Command {
	
	private static final String MESSAGE_LOCATION_SET = "Storage location set to: %1$s";
	private static final String MESSAGE_UNDONE = "Storage location reverted to: %1$s";
	private String newPathName;
	private String oldPathName;
	
	public CommandType getType() {
		return CommandType.SET;
	}
	
	public CommandSetLocation(String pathName) {
		newPathName = pathName;
	}
	
	public String execute() {
		return String.format(MESSAGE_LOCATION_SET, newPathName);
	}
	
	public String undo() {
		return String.format(MESSAGE_UNDONE, oldPathName);
	}
}