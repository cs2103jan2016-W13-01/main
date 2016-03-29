package logic.commands;

/**
 * @author Bao Linh
 * This class contains details for the "set location" commands
 */
import java.io.IOException;

import Storage.Storage;
import logic.ExecutedCommands;

public class CommandSetLocation implements Command {
	
	private static final String MESSAGE_LOCATION_SET = "Storage location set to: %1$s";
	private static final String MESSAGE_UNDONE = "Storage location reverted to: %1$s";
	private static final String MESSAGE_SET_ERROR = "Failed to set storage location to: %1$s";
	
	private String newPathName;
	private String oldPathName;
	
	public CommandType getType() {
		return CommandType.SET;
	}
	
	public CommandSetLocation(String pathName) {
		newPathName = pathName;
	}
	
	public String execute() {
		try {
			oldPathName = Storage.getPath();
			Storage.setPath(newPathName);
			ExecutedCommands.addCommand(this);
			return String.format(MESSAGE_LOCATION_SET, newPathName);
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_SET_ERROR, newPathName);
		}
	}
	
	public String undo() {
		try {
			Storage.setPath(oldPathName);
			return String.format(MESSAGE_UNDONE, oldPathName);
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_SET_ERROR, oldPathName);
		}
	}
}
