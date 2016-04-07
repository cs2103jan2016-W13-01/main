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
		String path = StorageController.getPath();
		System.out.println(path);
		return String.format(MESSAGE_LOCATION, path);
	}
	
	public String undo() {
		return null;
	}
}
