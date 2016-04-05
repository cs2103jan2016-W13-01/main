package logic.commands;

import storage.StorageController;

/* @@author A0112184R
 * This class contains details for the get location command
 */
public class CommandGetLocation implements Command {
	
	private static final String MESSAGE_LOCATION = "Storage location is: %s";

	public CommandType getType() {
		return CommandType.GET;
	}
	
	public CommandGetLocation() {}
	
	public String execute() {
		return String.format(MESSAGE_LOCATION, StorageController.getPath());
	}
	
	public String undo() {
		return null;
	}
}
