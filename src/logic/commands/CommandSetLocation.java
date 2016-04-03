package logic.commands;

/* @@author A0112184R
 * This class contains details for the "set location" commands
 */
import java.io.IOException;

import logic.ExecutedCommands;
import storage.StorageController;

public class CommandSetLocation implements Command {
	
	private static final String MESSAGE_LOCATION_SET = "storage location set to: %1$s";
	private static final String MESSAGE_UNDONE = "storage location reverted to: %1$s";
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
			oldPathName = StorageController.getPath();
			StorageController.setPath(newPathName);
			ExecutedCommands.addCommand(this);
			return String.format(MESSAGE_LOCATION_SET, newPathName);
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_SET_ERROR, newPathName);
		}
	}
	
	public String undo() {
		try {
			StorageController.setPath(oldPathName);
			return String.format(MESSAGE_UNDONE, oldPathName);
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_SET_ERROR, oldPathName);
		}
	}
}
